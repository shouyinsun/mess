package com.cash.something.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理LogEvent消息
 * author cash
 * create 2019-03-31-17:25
 **/
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuilder sb=new StringBuilder();
        sb.append(msg.getReceived());
        sb.append(" [");
        sb.append(msg.getSource().toString());
        sb.append("] [");
        sb.append(msg.getLogfile());
        sb.append("] : ");
        sb.append(msg.getMsg());
        System.out.println(sb.toString());
    }
}
