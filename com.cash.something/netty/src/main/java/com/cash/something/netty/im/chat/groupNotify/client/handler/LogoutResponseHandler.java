package com.cash.something.netty.im.chat.groupNotify.client.handler;

import com.cash.something.netty.im.chat.groupNotify.protocol.response.LogoutResponsePacket;
import com.cash.something.netty.im.chat.groupNotify.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
