package com.cash.something.netty.duplex;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * author cash
 * create 2019-01-09-0:19
 *
 * 实现cs端双向通信
 **/
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                //boss负责接收建立新连接  worker负责io操作、业务处理
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                })
                .bind(8000);//bind 操作返回 ChannelFuture,可以addListener
    }
}

class FirstServerHandler extends ChannelHandlerAdapter{


    //数据读取
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        ByteBuf bf=getByteBuf(ctx);
        ctx.channel().writeAndFlush(bf);
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "hello for what?".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
