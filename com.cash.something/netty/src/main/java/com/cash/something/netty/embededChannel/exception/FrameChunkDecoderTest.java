package com.cash.something.netty.embededChannel.exception;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * test for embeddedChannel
 * throw exception
 *
 * author cash
 * create 2019-03-24-22:32
 **/
public class FrameChunkDecoderTest {

    @Test
    public void test(){

        ByteBuf byteBuf= Unpooled.buffer();
        for(int i=0;i<9;i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input=byteBuf.duplicate();
        EmbeddedChannel channel=new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(channel.writeInbound(input.readBytes(2)));

        try {//exception
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        assertEquals(byteBuf.readSlice(2),channel.readInbound());

        assertEquals(byteBuf.skipBytes(4).readSlice(3),channel.readInbound());


        byteBuf.release();





    }
}
