package com.cash.somethingakka.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * author cash
 * create 2017-08-31-17:35
 **/

public class PrintActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) {
        log.info("akka.future.PrintActor.onReceive:" + o);
        if (o instanceof Integer) {
            log.info("print:" + o);
        } else {
            unhandled(o);
        }
    }
}
