package com.cash.something.netty.httpServer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * author cash
 * create 2019-01-07-22:27
 *
 * FullHttpRequest 完整的http 请求
 *
 * 在使用netty处理channel的时候,只处理消息是FullHttpRequest的Channel,
 * 这样我们就能在一个ChannelHandler中处理一个完整的Http请求了
 **/
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DefaultFullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                HttpResponseStatus.OK, Unpooled.wrappedBuffer("test".getBytes()));
        HttpHeaders heads=response.headers();
        heads.add(HttpHeaders.Names.CONTENT_TYPE,HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED+";charset=UTF-8");
        heads.add(HttpHeaders.Names.CONTENT_LENGTH,response.content().readableBytes());
        heads.add(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null !=cause) cause.printStackTrace();
        if(null !=ctx) ctx.close();
    }
}
