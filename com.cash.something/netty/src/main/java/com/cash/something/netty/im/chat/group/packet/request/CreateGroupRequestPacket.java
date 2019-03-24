package com.cash.something.netty.im.chat.group.packet.request;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Data;

import java.util.List;

/**
 * author cash
 * create 2019-02-27-23:19
 **/
@Data
public class CreateGroupRequestPacket  extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
