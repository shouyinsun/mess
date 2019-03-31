package com.cash.something.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * author cash
 * create 2019-03-31-12:41
 **/
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group){
        this.group=group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //HttpRequest、HttpContent、LastHttpContent 与字节码相互转换
        pipeline.addLast(new HttpServerCodec());
        //写入一个文件内容
        pipeline.addLast(new ChunkedWriteHandler());
        //将一个HttpRequest和跟随它的多个HttpContent聚合为单个HttpFullRequest或HttpFullResponse
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        //处理FullHttpRequest,那些不发送到/ws的请求
        pipeline.addLast(new HttpRequestHandler("/ws"));
        //webSocket规范,处理升级握手、ping、pong、closeWebSocket帧
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //处理TextWebSocketFrame和握手完成事件
        pipeline.addLast(new TextWebSocketFrameHandler(group));

    }
}
