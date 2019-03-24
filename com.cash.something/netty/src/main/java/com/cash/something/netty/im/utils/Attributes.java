package com.cash.something.netty.im.utils;

import com.cash.something.netty.im.chat.p2p.Session;
import io.netty.util.AttributeKey;

/**
 * author cash
 * create 2019-02-18-23:20
 **/
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.valueOf("login");

    AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
