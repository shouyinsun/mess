package com.cash.something.netty.duplex;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * author cash
 * create 2019-01-09-0:23
 *
 * 实现cs端双向通信
 **/
public class NettyClient {
    private static final int MAX_RETRY=10;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        //connect返回的是一个channelFuture,可以添加listener
        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

    }

}

class FirstClientHandler extends ChannelHandlerAdapter {

    //客户端连接建立成功之后 调用channelActive()方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");
        ByteBuf byteBuf=getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf=ctx.alloc().buffer();
        byte[] bytes=new String("hello,cash").getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(bytes);
        return  byteBuf;
    }

    //数据读取
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
