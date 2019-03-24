package com.cash.something.netty.im.handler.request;

import com.cash.something.netty.im.protocal.request.MessageRequestPacket;
import com.cash.something.netty.im.protocal.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 消息 request handler
 * author cash
 * create 2019-02-20-23:39
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket>{
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
