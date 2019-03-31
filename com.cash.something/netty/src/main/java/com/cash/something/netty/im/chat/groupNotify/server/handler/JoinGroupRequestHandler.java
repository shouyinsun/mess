package com.cash.something.netty.im.chat.groupNotify.server.handler;

import com.cash.something.netty.im.chat.groupNotify.protocol.request.JoinGroupRequestPacket;
import com.cash.something.netty.im.chat.groupNotify.protocol.response.JoinGroupResponsePacket;
import com.cash.something.netty.im.chat.groupNotify.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;


public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        //获取群对应的 channelGroup,然后将当前用户的 channel 添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        //构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();

        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(responsePacket);
    }

}
