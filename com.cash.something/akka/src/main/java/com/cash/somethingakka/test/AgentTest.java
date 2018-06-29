package com.cash.somethingakka.test;

import akka.actor.*;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnComplete;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * author cash
 * create 2017-08-31-18:11
 * 使用agent来实现共享变量的安全处理
 **/

public class AgentTest extends UntypedActor{



    @Override
    public void onReceive(Object o){
        if(o instanceof Integer){
            Integer sum=0;
            for(int i = 0 ; i < 10000 ; i ++){
                 sum+=i;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getSender().tell(sum,getSelf());
            getContext().stop(getSelf());//完成任务，关闭自己
        }else{
            unhandled(o);
        }
    }


    public static void main(String [] args) throws InterruptedException{
        ActorSystem system = ActorSystem.create("inbox", ConfigFactory.load("akka.conf"));

        Worker w1=new Worker(system);
        Thread t1=new Thread(w1);

        Worker w2=new Worker(system);
        Thread t2=new Thread(w2);


        Worker w3=new Worker(system);
        Thread t3=new Thread(w3);


        Worker w4=new Worker(system);
        Thread t4=new Thread(w4);

        Worker w5=new Worker(system);
        Thread t5=new Thread(w5);

        t1.start();
        t3.start();
        t2.start();
       /* t4.start();
        t5.start();*/


    }


}
