package com.cash.somethingakka.unmodifiable;

import akka.actor.UntypedActor;
import com.alibaba.fastjson.JSONObject;

/**
 * author cash
 * create 2017-08-30-19:54
 **/

public class Greeter extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        try {
            System.out.println("Greeter收到的数据为：" + JSONObject.toJSONString(message));
            Message message1= (Message) message;
            message1.getList().add("xxxxxxx");
            getSender().tell("Greeter工作完成。", getSelf());//给发送至发送信息.
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
