package com.cash.something.netty.im.chat.optimize.protocol.request;

import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
