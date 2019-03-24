package com.cash.something.netty.embededChannel.exception;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * author cash
 * create 2019-03-24-22:29
 **/
public class FrameChunkDecoder extends ByteToMessageDecoder {
    private final int  maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>maxFrameSize){
            in.clear();
            throw new TooLongFrameException();
        }

        out.add(in.readBytes(in.readableBytes()));
    }
}
