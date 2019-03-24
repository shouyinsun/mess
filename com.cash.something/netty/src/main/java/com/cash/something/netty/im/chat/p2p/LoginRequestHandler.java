package com.cash.something.netty.im.chat.p2p;

import com.cash.something.netty.im.protocal.request.LoginRequestPacket;
import com.cash.something.netty.im.protocal.response.LoginResponsePacket;
import com.cash.something.netty.im.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * author cash
 * create 2019-02-24-11:35
 **/
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            String userId = UUID.randomUUID().toString();
            loginResponsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //session解绑
        SessionUtil.unBindSession(ctx.channel());
    }
}
