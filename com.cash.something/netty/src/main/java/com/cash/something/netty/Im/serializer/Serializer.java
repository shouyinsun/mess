package com.cash.something.netty.im.serializer;

import com.cash.something.netty.im.serializer.impl.JSONSerializer;

/**
 * 序列化接口
 * author cash
 * create 2019-01-14-22:59
 **/
public interface Serializer {

    //序列化算法
    byte getSerializerAlgorithm();

    //序列化
    byte[] serializer(Object object);

    //反序列化
    <T> T deserialize(Class<T> clazz,byte[] bytes);


    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();


}
