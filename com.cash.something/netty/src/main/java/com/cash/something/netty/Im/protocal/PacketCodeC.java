package com.cash.something.netty.im.protocal;

import com.cash.something.netty.im.protocal.command.Command;
import com.cash.something.netty.im.protocal.request.LoginRequestPacket;
import com.cash.something.netty.im.protocal.request.MessageRequestPacket;
import com.cash.something.netty.im.protocal.response.LoginResponsePacket;
import com.cash.something.netty.im.protocal.response.MessageResponsePacket;
import com.cash.something.netty.im.serializer.Serializer;
import com.cash.something.netty.im.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据编解码器
 * author cash
 * create 2019-01-14-23:09
 **/
public class PacketCodeC {

    //魔数
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {//command 与 serializer 缓存
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet){
        //创建buteBuf,ioBuffer() 方法会返回适配 io 读写相关的内存,会尽可能创建一个直接内存
        //ByteBuf byteBuf= UnpooledByteBufAllocator.DEFAULT.ioBuffer();
        ByteBuf byteBuf= byteBufAllocator.ioBuffer();
        //序列化java对象
        byte[] bytes=Serializer.DEFAULT.serializer(packet);

        //实际的编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes=Serializer.DEFAULT.serializer(packet);

        //实际的编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    public Packet decode(ByteBuf byteBuf){
        //跳过 magic number
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializeAlgorithm =byteBuf.readByte();
        //指令
        byte command=byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();

        //数据
        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType=getRequestType(command);

        Serializer serializer=getSerializer(serializeAlgorithm);

        return serializer.deserialize(requestType,bytes);

    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
