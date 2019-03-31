package com.cash.something.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * LogEvent 解码器
 * author cash
 * create 2019-03-31-17:17
 **/
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf data=datagramPacket.content();
        int idx=data.indexOf(0,data.readableBytes(),LogEvent.SEPARATOR);
        String fileName=data.slice(0,idx).toString(CharsetUtil.UTF_8);
        String logMsg=data.slice(idx+1,data.readableBytes()).toString(CharsetUtil.UTF_8);
        LogEvent logEvent=new LogEvent(datagramPacket.sender(),fileName,logMsg,System.currentTimeMillis());
        out.add(logEvent);
    }
}
