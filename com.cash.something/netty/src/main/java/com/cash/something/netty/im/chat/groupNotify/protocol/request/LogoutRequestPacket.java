package com.cash.something.netty.im.chat.groupNotify.protocol.request;

import com.cash.something.netty.im.chat.groupNotify.protocol.Packet;
import com.cash.something.netty.im.chat.groupNotify.protocol.command.Command;
import lombok.Data;


@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return Command.LOGOUT_REQUEST;
    }
}
