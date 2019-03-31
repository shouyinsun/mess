package com.cash.something.netty.im.chat.groupNotify.server.handler;

import com.cash.something.netty.im.chat.groupNotify.protocol.request.MessageRequestPacket;
import com.cash.something.netty.im.chat.groupNotify.protocol.response.MessageResponsePacket;
import com.cash.something.netty.im.chat.groupNotify.session.Session;
import com.cash.something.netty.im.chat.groupNotify.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        //拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        //通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线,发送失败!");
        }
    }
}
