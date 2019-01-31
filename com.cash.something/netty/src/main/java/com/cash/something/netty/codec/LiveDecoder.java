package com.cash.something.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * author cash
 * create 2019-01-07-23:38
 *
 *  ReplayingDecoder 是 byte-to-message 解码的一种特殊的抽象基类
 *  读取缓冲区的数据之前需要检查缓冲区是否有足够的字节
 *  使用ReplayingDecoder就无需自己检查；若ByteBuf中有足够的字节,则会正常读取；若没有足够的字节则会停止解码
 *
 *
 *  ReplayingDecoder 略慢于 ByteToMessageDecoder
 *  所以,如果不引入过多的复杂性 使用 ByteToMessageDecoder
 *  否则,使用ReplayingDecoder
 **/
public class LiveDecoder extends ReplayingDecoder<LiveDecoder.LiveState> {

    private LiveMessage message=new LiveMessage();

    public LiveDecoder() {
        super(LiveState.LENGTH); // 初始化的时候设置为读取长度的状态
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){//读取的时候通过state()方法来确定当前处于什么状态
            case LENGTH:
                int length=in.readInt();
                if(length>0){
                    checkpoint(LiveState.CONTENT);
                }else{
                    out.add(message);
                }
                break;

            case CONTENT:
                byte[] bytes=new byte[message.getLength()];
                in.readBytes(bytes);
                String content=new String(bytes);
                message.setContent(content);
                out.add(message);
                break;

            default:
                throw new IllegalStateException("invalid state:" + state());


        }
    }

    public enum LiveState {
        LENGTH,
        CONTENT
    }

    class LiveMessage{
        private int length;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
