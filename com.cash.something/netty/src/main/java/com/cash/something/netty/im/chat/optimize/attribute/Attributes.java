package com.cash.something.netty.im.chat.optimize.attribute;

import io.netty.util.AttributeKey;
import com.cash.something.netty.im.chat.optimize.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
