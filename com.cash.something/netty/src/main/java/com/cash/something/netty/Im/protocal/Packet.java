package com.cash.something.netty.im.protocal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * author cash 数据包
 * create 2019-01-14-22:50
 **/
@Data
public abstract class Packet {
    //协议版本
    @JSONField(deserialize = false, serialize = false)
    private Byte version=1;

    //指令
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
