package com.cash.something.netty.im.chat.optimize.server;

import com.cash.something.netty.im.chat.optimize.IMIdleStateHandler;
import com.cash.something.netty.im.chat.optimize.codec.PacketCodecHandler;
import com.cash.something.netty.im.chat.optimize.codec.Spliter;
import com.cash.something.netty.im.chat.optimize.server.handler.AuthHandler;
import com.cash.something.netty.im.chat.optimize.server.handler.HeartBeatRequestHandler;
import com.cash.something.netty.im.chat.optimize.server.handler.IMHandler;
import com.cash.something.netty.im.chat.optimize.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                //ServerBootstrapChannelFactory 的 class 为 NioServerSocketChannel.class
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //连接假死,处理闲置
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        //加上注解标识表明该 handler 是可以多个channel共享的
                        //使用单例,不用每个连接进来都new
                        //handler,他们内部都是没有成员变量的,是无状态的

                        //MessageToMessageCodec 编解码器
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        //心跳消息
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        //合并平行handler,把多个指令 handler压缩为一个 handler
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });


        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
