package com.cash.something.netty.im.chat.optimize.protocol.request;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
