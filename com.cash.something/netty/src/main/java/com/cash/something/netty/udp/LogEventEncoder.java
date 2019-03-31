package com.cash.something.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * LogEvent 编码器
 * author cash
 * create 2019-03-31-16:13
 **/
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress address){
        this.remoteAddress=address;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        byte[] file=logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg=logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf=ctx.alloc().buffer(file.length+msg.length+1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msg);
        //DatagramPacket
        out.add(new DatagramPacket(buf,remoteAddress));
    }
}
