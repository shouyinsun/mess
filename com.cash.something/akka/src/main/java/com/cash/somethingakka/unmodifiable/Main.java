package com.cash.somethingakka.unmodifiable;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * author cash
 * create 2017-08-30-19:57
 **/

public class Main {
    //  public static void main(String[] args) {
    //    akka.Main.main(new String[] { HelloWorld.class.getName() });
    //  }

    public static void main(String[] args) {
        //创建ActorSystem。一般来说，一个系统只需要一个ActorSystem。
        //参数1：系统名称。参数2：配置文件
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("akka.config"));
        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        System.out.println(a.path());
    }
}
