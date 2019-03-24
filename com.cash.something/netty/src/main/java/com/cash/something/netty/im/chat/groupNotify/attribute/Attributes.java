package com.cash.something.netty.im.chat.groupNotify.attribute;

import com.cash.something.netty.im.chat.groupNotify.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.valueOf("session");
}
