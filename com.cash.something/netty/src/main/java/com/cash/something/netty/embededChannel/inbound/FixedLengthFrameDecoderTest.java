package com.cash.something.netty.embededChannel.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * test for EmbeddedChannel
 *
 * EmbeddedChannel,添加 LastInboundHandler 到最后面
 * author cash
 * create 2019-03-24-21:49
 **/
public class FixedLengthFrameDecoderTest {

    @Test
    public void  test1(){
        ByteBuf byteBuf= Unpooled.buffer();
        for(int i=0;i<9;i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input=byteBuf.duplicate();

        //添加channel,可以多个,addLast
        EmbeddedChannel channel=new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        ByteBuf read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        byteBuf.release();
    }


    @Test
    public void  test2(){
        ByteBuf byteBuf= Unpooled.buffer();
        for(int i=0;i<9;i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input=byteBuf.duplicate();
        //添加channel,可以多个,addLast
        EmbeddedChannel channel=new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
        assertTrue(channel.finish());

        ByteBuf read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();


        read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();


        read=channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        byteBuf.release();


    }
}
