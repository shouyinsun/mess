package com.cash.something.netty.im.utils;

import java.util.UUID;

/**
 * id
 * author cash
 * create 2019-02-27-23:12
 **/
public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
