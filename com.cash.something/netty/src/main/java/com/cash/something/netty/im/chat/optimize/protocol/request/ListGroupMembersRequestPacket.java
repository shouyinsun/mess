package com.cash.something.netty.im.chat.optimize.protocol.request;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
