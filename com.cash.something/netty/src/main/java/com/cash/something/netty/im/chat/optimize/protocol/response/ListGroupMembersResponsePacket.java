package com.cash.something.netty.im.chat.optimize.protocol.response;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;
import com.cash.something.netty.im.chat.optimize.session.Session;

import java.util.List;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
