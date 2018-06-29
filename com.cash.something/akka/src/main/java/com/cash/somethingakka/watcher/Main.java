package com.cash.somethingakka.watcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * author cash
 * create 2017-08-31-11:11
 **/

public class Main {
    public static void main(String[] args) {
        //创建ActorSystem。一般来说，一个系统只需要一个ActorSystem。
        //参数1：系统名称。参数2：配置文件
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("akka.config"));
        ActorRef work = system.actorOf(Props.create(Work.class), "MyWork");
        ActorRef watchActor = system.actorOf(Props.create(WatchActor.class, work), "WatchActor");
        System.out.println(watchActor.path());

        work.tell(Work.Msg.WORKING, ActorRef.noSender());
        work.tell(Work.Msg.DONE, ActorRef.noSender());

        //中断myWork
        work.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
