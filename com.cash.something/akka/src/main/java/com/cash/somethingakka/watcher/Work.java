package com.cash.somethingakka.watcher;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * author cash
 * create 2017-08-31-10:57
 **/

public class Work extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public  enum Msg{
        WORKING, DONE, CLOSE;
    }


    @Override
    public void preStart() {
        logger.info("Work starting.");
    }

    @Override
    public void postStop() throws Exception {
        logger.info("Work stoping..");
    }

    @Override
    public void onReceive(Object msg) throws Exception {

        try {
            if(msg == Msg.WORKING){
                logger.info("i am  working");
            }else if(msg == Msg.DONE){
                logger.info("stop  working");
            }else if(msg == Msg.CLOSE){
                logger.info("stop  close");
                getSender().tell(Msg.CLOSE, getSelf());
                getContext().stop(getSelf());
            }else {
                unhandled(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
