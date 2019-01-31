package com.cash.something.netty.httpServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * author cash
 * create 2019-01-07-0:01
 *
 * 搭建HttpServer
 *
 **/
public class HttpServer {
    private final int port;

    public HttpServer(int port){
        this.port=port;
    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length!=1){
            return;
        }
        int port= Integer.parseInt(args[0]);
        new HttpServer(port).start();

    }

    public void start() throws InterruptedException {
        ServerBootstrap b=new ServerBootstrap();
        NioEventLoopGroup group=new NioEventLoopGroup();
        b.group(group).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("initChannel ch:"+socketChannel);
                        socketChannel.pipeline()
                                .addLast("decoder",new HttpRequestDecoder())
                                .addLast("encoder",new HttpResponseEncoder())
                                //没有aggregator,那么一个http请求就会通过多个Channel被处理
                                .addLast("aggregator",new HttpObjectAggregator(512*1024))
                                .addLast("handler",new HttpHandler());
                    }
                }).option(ChannelOption.SO_BACKLOG,128)
                .option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);

        b.bind(port).sync();
    }

}
