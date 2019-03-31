package com.cash.something.netty.im.chat.groupNotify.server.handler;

import com.cash.something.netty.im.chat.groupNotify.protocol.request.ListGroupMembersRequestPacket;
import com.cash.something.netty.im.chat.groupNotify.protocol.response.ListGroupMembersResponsePacket;
import com.cash.something.netty.im.chat.groupNotify.session.Session;
import com.cash.something.netty.im.chat.groupNotify.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {
        //获取群的 ChannelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        //遍历群成员的 channel,对应的 session,构造群成员的信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        //构建获取成员列表响应写回到客户端
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();

        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
