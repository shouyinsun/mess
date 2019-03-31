package com.cash.something.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * author cash
 * create 2019-03-31-12:53
 **/
public class ChatServer {
    private final ChannelGroup channelGroup=new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group=new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(createInitializer(channelGroup));
        ChannelFuture future=bootstrap.bind(address);
        future.syncUninterruptibly();
        channel=future.channel();
        return  future;
    }

    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group){
        return new ChatServerInitializer(group);
    }

    public void destroy(){
        if(channel!=null){
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
//        if(args.length!=1){
//            System.exit(1);
//        }
        int port=9090;
        final ChatServer endpoint=new ChatServer();
        ChannelFuture future=endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> endpoint.destroy()));

        future.channel().closeFuture().syncUninterruptibly();
    }
}
