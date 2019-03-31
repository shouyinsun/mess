package com.cash.something.netty.im.chat.group.handler.requset;

import com.cash.something.netty.im.chat.p2p.Session;
import com.cash.something.netty.im.chat.group.SessionUtil;
import com.cash.something.netty.im.chat.group.packet.request.MessageRequestPacket;
import com.cash.something.netty.im.chat.group.packet.response.MessageResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * author cash
 * create 2019-02-27-23:49
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
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
