package com.cash.somethingakka.sample;

import akka.actor.UntypedActor;

/**
 * author cash
 * create 2017-08-30-19:36
 **/

public class Greeter extends UntypedActor {

    public enum Msg {
        GREET, DONE;
    }

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg == Msg.GREET) {
            System.out.println("Hello World!");
            Thread.sleep(1000);
            getSender().tell(Msg.DONE, getSelf());
        } else
            unhandled(msg);

    }
}
