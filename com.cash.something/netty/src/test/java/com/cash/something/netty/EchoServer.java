package com.cash.something.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * author cash
 * create 2018-06-10-14:54
 **/

public class EchoServer {
    private int port;


    public EchoServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); //创建serverBootstrap实例
            b.group(bossGroup, workerGroup)//绑定Reactor线程池,netty的Reactor的线程池是EventLoopGroup
                    .channel(NioServerSocketChannel.class)//设置并绑定服务端channel
                    //链路建立的时候创建并初始化ChannelPipeline(非必须),职责链,管理和执行channelHandler
                    .childHandler(new ChannelInitializer<SocketChannel>() { //添加设置channelHandler
                 public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 128)          //
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); //绑定并启动监听端口
            // Wait until the server socket is closed.
            // In this example,this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }


    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new EchoServer(port).run();
    }
}
