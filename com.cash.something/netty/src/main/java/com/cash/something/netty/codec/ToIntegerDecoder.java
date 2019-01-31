package com.cash.something.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * author cash
 * create 2019-01-07-23:35
 *
 * byte to Integer 解码
 **/
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //不知道这次请求发过来多少数据,所以每次都要判断byte长度够不够4
        if(in.readableBytes()>=4){
            out.add(in.readInt());
        }
    }
}
