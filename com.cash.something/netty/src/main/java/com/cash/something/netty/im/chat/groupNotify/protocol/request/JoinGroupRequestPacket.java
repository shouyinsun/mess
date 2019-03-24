package com.cash.something.netty.im.chat.groupNotify.protocol.request;

import com.cash.something.netty.im.chat.groupNotify.protocol.Packet;
import com.cash.something.netty.im.chat.groupNotify.protocol.command.Command;
import lombok.Data;


@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_REQUEST;
    }
}
