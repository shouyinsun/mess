package com.cash.something.netty.Im.client;

import com.cash.something.netty.Im.protocal.Packet;
import com.cash.something.netty.Im.protocal.PacketCodeC;
import com.cash.something.netty.Im.protocal.request.LoginRequestPacket;
import com.cash.something.netty.Im.protocal.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * author cash
 * create 2019-01-15-23:23
 **/
public class ClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+":客户端开始登录");

        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
        loginRequestPacket.setUserId(100);
        loginRequestPacket.setUserName("cash");
        loginRequestPacket.setPassWord("cash");

        ByteBuf reqByteBuf= PacketCodeC.INSTANCE.encode(ctx.alloc(),loginRequestPacket);
        ctx.channel().writeAndFlush(reqByteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket= (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }

    }
}
