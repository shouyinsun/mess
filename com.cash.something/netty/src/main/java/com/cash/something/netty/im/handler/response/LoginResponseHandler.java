package com.cash.something.netty.im.handler.response;

import com.cash.something.netty.im.protocal.response.LoginResponsePacket;
import com.cash.something.netty.im.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 登录 response handler
 * author cash
 * create 2019-02-20-23:42
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            System.out.println("userId:"+msg.getUserId());
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败,原因：" + msg.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(1000);
//        loginRequestPacket.setUserName("cash");
//        loginRequestPacket.setPassWord("cash");
//
//        // 写数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
    }
}
