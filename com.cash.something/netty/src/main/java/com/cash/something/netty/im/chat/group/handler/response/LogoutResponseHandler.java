package com.cash.something.netty.im.chat.group.handler.response;

import com.cash.something.netty.im.chat.group.SessionUtil;
import com.cash.something.netty.im.chat.group.packet.response.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * author cash
 * create 2019-02-27-23:40
 **/
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
