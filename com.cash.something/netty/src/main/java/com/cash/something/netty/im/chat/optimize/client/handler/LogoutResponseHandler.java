package com.cash.something.netty.im.chat.optimize.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.cash.something.netty.im.chat.optimize.protocol.response.LogoutResponsePacket;
import com.cash.something.netty.im.chat.optimize.util.SessionUtil;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
