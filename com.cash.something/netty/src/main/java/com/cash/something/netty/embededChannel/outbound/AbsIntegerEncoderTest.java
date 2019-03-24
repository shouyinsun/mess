package com.cash.something.netty.embededChannel.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * test for EmbeddedChannel
 *
 * author cash
 * create 2019-03-24-22:19
 **/
public class AbsIntegerEncoderTest {

    @Test
    public void test(){
        ByteBuf byteBuf= Unpooled.buffer();
        for(int i=0;i<10;i++){
            byteBuf.writeInt(i*-1);
        }
        EmbeddedChannel channel=new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(byteBuf));
        assertTrue(channel.finish());

        for(int i=0;i<10;i++){
            assertEquals((Object) i,channel.readOutbound());
        }

        assertNull(channel.readOutbound());

    }


}
