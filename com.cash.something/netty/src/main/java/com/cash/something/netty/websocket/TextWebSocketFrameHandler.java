
package com.cash.something.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 处理webSocket 文本帧
 * author cash
 * create 2019-03-31-12:27
 **/
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group){
        this.group=group;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //写到ChannelGroup中所有已经连接的客户端
        group.writeAndFlush(msg.retain());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt== WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            //握手成功,从pipeline中移除HttpRequestHandler,因为将不会接收到任何http消息
            ctx.pipeline().remove(HttpRequestHandler.class);
            //通知所有已经连接的webSocket客户端新的客户端已经连接上
            group.writeAndFlush(new TextWebSocketFrame("Client "+ctx.channel()+" joined"));
            //添加新的webSocket channel 到ChannelGroup
            group.add(ctx.channel());
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }
}
