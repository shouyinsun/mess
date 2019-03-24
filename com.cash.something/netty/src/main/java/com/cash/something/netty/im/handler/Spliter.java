package com.cash.something.netty.im.handler;

import com.cash.something.netty.im.protocal.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拒绝不同协议的连接
 * author cash
 * create 2019-02-23-18:46
 **/
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;//length属性偏移量
    private static final int LENGTH_FIELD_LENGTH = 4;//length属性自身长度

    public Spliter() {
        //LengthFieldBasedFrameDecoder ,拆包
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        // 屏蔽非本协议的客户端,魔数不匹配
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
