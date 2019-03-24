package com.cash.something.netty.im.chat.optimize.protocol.request;

import lombok.Data;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
