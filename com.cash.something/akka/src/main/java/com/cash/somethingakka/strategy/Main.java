package com.cash.somethingakka.strategy;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * author cash
 * create 2017-08-31-14:53
 **/

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("strategy", ConfigFactory.load("akka.config"));
        ActorRef superVisor = system.actorOf(Props.create(SuperVisor.class), "SuperVisor");

        superVisor.tell(Props.create(RestartActor.class), ActorRef.noSender());


        //选择actor  通过actorSelection方便的选择actor进行消息投递
        //支持通配符匹配getContext().actorSelection("/user/worker_*")
        ActorSelection actorSelection = system.actorSelection("akka://strategy/user/SuperVisor/restartActor");//这是akka的路径。restartActor是在SuperVisor中创建的。

        for(int i = 0 ; i < 10 ; i ++){
            actorSelection.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
        }
    }
}
