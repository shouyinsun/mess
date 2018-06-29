package com.cash.somethingakka.test;

import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * author cash
 * create 2017-11-23-19:17
 **/

public class Worker implements  Runnable {
    ActorSystem system;

    public Worker(ActorSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        ActorRef[] actorRefs = new ActorRef[20];
        for(int i = 0 ; i < 20 ; i ++){
            actorRefs[i] = system.actorOf(Props.create(AgentTest.class), "AgentTest" +Thread.currentThread().getName()+ System.currentTimeMillis()+i);

        }

        Inbox inbox = Inbox.create(system);
        for (ActorRef ref : actorRefs) {
            inbox.send(ref, 1);
            inbox.watch(ref);
        }

        //等待所有actor执行完毕
        int closeCount = 0;
        long sum=0;
        long startTime=System.currentTimeMillis();
        int cycles=0;
        while(true){
            cycles++;
            long  now=System.currentTimeMillis();
            if((now-startTime)>1500){
                for(ActorRef actorRef:actorRefs){
                    actorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
                }
                System.out.println(Thread.currentThread().getName()+"------------------ timeout,closeCount:"+closeCount);
                System.out.println(Thread.currentThread().getName()+"------------------ timeout,cycles:"+cycles);
                break;
            }
            try {
                Object o = inbox.receive(Duration.create(100, TimeUnit.MILLISECONDS));
                if(o instanceof Terminated){
                    closeCount ++;
                    if(closeCount == actorRefs.length){
                        System.out.println(Thread.currentThread().getName()+"------------------ done,closeCount:"+closeCount);
                        System.out.println(Thread.currentThread().getName()+"------------------ done,cycles:"+cycles);

                        break;
                    }
                }else if(o instanceof Integer){
                    sum+=(Integer)o;
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName()+"------------------ sum:"+sum);

        long endTime=System.currentTimeMillis();

        System.out.println(Thread.currentThread().getName()+"------------------ waste time:"+(endTime-startTime));


    }
}
