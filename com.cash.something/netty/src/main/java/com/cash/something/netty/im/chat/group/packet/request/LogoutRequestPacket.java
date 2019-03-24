package com.cash.something.netty.im.chat.group.packet.request;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Data;

/**
 * author cash
 * create 2019-02-27-23:26
 **/
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
