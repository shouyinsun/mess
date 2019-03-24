package com.cash.something.netty.im.handler;

import com.cash.something.netty.im.utils.LoginUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 热插拔的权限认证
 * author cash
 * create 2019-02-23-21:33
 **/
public class AuthHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //登录过的,pipeline中删除自身
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
