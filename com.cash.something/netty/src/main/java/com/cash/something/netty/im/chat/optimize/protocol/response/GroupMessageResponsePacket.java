package com.cash.something.netty.im.chat.optimize.protocol.response;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;
import com.cash.something.netty.im.chat.optimize.session.Session;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
