package com.cash.somethingakka.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * author cash
 * create 2017-08-31-17:36
 **/

public class WorkerActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o)  {
        log.info("akka.future.WorkerActor.onReceive:" + o);

        if (o instanceof Integer) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = Integer.parseInt(o.toString());
            getSender().tell(i*i, getSelf());
        } else {
            unhandled(o);
        }
    }
}
