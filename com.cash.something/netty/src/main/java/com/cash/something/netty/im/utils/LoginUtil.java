package com.cash.something.netty.im.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * author cash
 * create 2019-02-18-23:19
 **/
public class LoginUtil {

    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
//        if(null==channel.attr(Attributes.LOGIN)) return false;
//        return channel.attr(Attributes.LOGIN).get();
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
