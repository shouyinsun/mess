package com.cash.somethingakka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * author cash
 * create 2017-08-30-19:44
 **/

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Hello");
        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        System.out.println(a.path());
    }
}
