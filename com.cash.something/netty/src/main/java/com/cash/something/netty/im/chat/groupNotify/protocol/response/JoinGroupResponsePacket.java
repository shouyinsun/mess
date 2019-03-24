package com.cash.something.netty.im.chat.groupNotify.protocol.response;

import com.cash.something.netty.im.chat.groupNotify.protocol.Packet;
import com.cash.something.netty.im.chat.groupNotify.protocol.command.Command;
import lombok.Data;


@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return Command.JOIN_GROUP_RESPONSE;
    }
}
