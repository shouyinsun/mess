package com.cash.something.netty.im.chat.group.packet.response;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Data;

import java.util.List;

/**
 * author cash
 * create 2019-02-27-23:25
 **/
@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
