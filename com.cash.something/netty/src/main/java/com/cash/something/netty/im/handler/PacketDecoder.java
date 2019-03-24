package com.cash.something.netty.im.handler;

import com.cash.something.netty.im.chat.group.codec.PacketCodeC;
import com.cash.something.netty.im.protocal.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * author cash 包解码器
 * create 2019-02-20-23:21
 **/
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet= PacketCodeC.INSTANCE.decode(in);
        out.add(packet);
    }
}
