package com.cash.something.netty.Im;

import com.cash.something.netty.Im.protocal.Packet;
import com.cash.something.netty.Im.protocal.PacketCodeC;
import com.cash.something.netty.Im.protocal.request.LoginRequestPacket;
import com.cash.something.netty.Im.serializer.Serializer;
import com.cash.something.netty.Im.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * author cash
 * create 2019-01-14-23:45
 **/
public class PacketCodeCTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassWord("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(UnpooledByteBufAllocator.DEFAULT,loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serializer(loginRequestPacket), serializer.serializer(decodedPacket));

    }
}
