package com.cash.something.netty.live;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * author cash
 * create 2019-01-08-0:07
 **/
public class LiveHandler extends SimpleChannelInboundHandler<LiveMessage> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LiveMessage msg) throws Exception {

    }
}
