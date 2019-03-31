package com.cash.something.netty.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

/**
 * 处理http请求
 * author cash
 * create 2019-03-31-11:11
 **/
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    public HttpRequestHandler(String wsUri){
        this.wsUri=wsUri;
    }

    static {
        URL location=HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path=location.toURI()+"index.html";
            path=path.contains("file:")?path.substring(5):path;
            INDEX=new File(path);
        }catch (Exception e){
            throw new IllegalStateException("unable to locate index.html",e);
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //请求webSocket协议升级
        if(wsUri.equalsIgnoreCase(request.getUri())){
            ctx.fireChannelRead(request.retain());
        }else{
            //100 continue
            if(HttpHeaders.is100ContinueExpected(request)){
                send100Continue(ctx);
            }
            //index.html
            RandomAccessFile file=new RandomAccessFile(INDEX,"r");
            HttpResponse response=new DefaultFullHttpResponse(request.getProtocolVersion(),HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/html;charset=UTF-8");
            boolean keepAlive=HttpHeaders.isKeepAlive(request);
            //keepAlive
            if(keepAlive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
            //如果没有ssl跟压缩,使用FileRegion,zero-copy
            if(ctx.pipeline().get(SslHandler.class) ==null){
                ctx.write(new DefaultFileRegion(file.getChannel(),0, file.length()));
            }else{
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future=ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(!keepAlive){//非keepAlive,完成之后close channel
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
