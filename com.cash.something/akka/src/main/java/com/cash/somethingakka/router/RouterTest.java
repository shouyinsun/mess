package com.cash.somethingakka.router;

import akka.actor.*;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.cash.somethingakka.inbox.InboxTest;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一组actor提供相同的服务,们在调用任务的时候只需要选择其中一个actor进行处理即可
 * 负载均衡/路由策略
 * author cash
 * create 2017-08-31-15:49
 **/

public class RouterTest extends UntypedActor{

    public Router router;

    public  static AtomicBoolean flag = new AtomicBoolean(true);

    {
        ArrayList<Routee> routees = new ArrayList<Routee>();
        for(int i = 0; i < 5; i ++) {
            ActorRef worker = getContext().actorOf(Props.create(InboxTest.class), "worker_" + i);
            //监听,中断,则从路由表中删除
            getContext().watch(worker);
            routees.add(new ActorRefRoutee(worker));
        }
        /**
         * RoundRobinRoutingLogic: 轮询
         * BroadcastRoutingLogic: 广播
         * RandomRoutingLogic: 随机
         * SmallestMailboxRoutingLogic: 空闲
         */
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object o){
        if(o instanceof InboxTest.Msg){
            router.route(o, getSender());//进行路由转发
        }else if(o instanceof Terminated){
            router = router.removeRoutee(((Terminated)o).actor());//发生中断,将该actor删除。当然也可以设置actor重启策略,进行优化
            System.out.println(((Terminated)o).actor().path() + " 该actor已经删除。router.size=" + router.routees().size());

            if(router.routees().size() == 0){//没有可用actor了
                System.out.print("没有可用actor了,系统关闭。");
                flag.compareAndSet(true, false);
                getContext().system().shutdown();
            }
        }else {
            unhandled(o);
        }

    }


    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("strategy", ConfigFactory.load("akka.config"));
        ActorRef routerTest = system.actorOf(Props.create(RouterTest.class), "RouterTest");

        int i = 1;
        while(flag.get()){
            routerTest.tell(InboxTest.Msg.WORKING, ActorRef.noSender());

            if(i % 10 == 0) routerTest.tell(InboxTest.Msg.CLOSE, ActorRef.noSender());

            Thread.sleep(500);

            i ++;
        }
    }

}
