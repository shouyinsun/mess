package com.cash.something.netty.im.chat.groupNotify.server.handler;

import com.cash.something.netty.im.chat.groupNotify.protocol.request.QuitGroupRequestPacket;
import com.cash.something.netty.im.chat.groupNotify.protocol.response.QuitGroupResponsePacket;
import com.cash.something.netty.im.chat.groupNotify.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;


public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) {
        //获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        //构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        responsePacket.setGroupId(requestPacket.getGroupId());
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);

    }
}
