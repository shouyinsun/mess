package com.cash.something.netty.Im.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.cash.something.netty.Im.serializer.Serializer;
import com.cash.something.netty.Im.serializer.SerializerAlgorithm;

/**
 * author cash json 序列化
 * create 2019-01-14-23:03
 **/
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
