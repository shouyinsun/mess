package com.cash.something.netty.im.chat.optimize.protocol.request;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import java.util.List;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
