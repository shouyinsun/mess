package com.cash.something.netty.im.handler;

import com.cash.something.netty.im.chat.group.codec.PacketCodeC;
import com.cash.something.netty.im.protocal.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * author cash 包编码器
 * create 2019-02-20-23:24
 **/
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
