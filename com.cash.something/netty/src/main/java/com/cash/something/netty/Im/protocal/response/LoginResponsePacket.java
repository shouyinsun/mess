package com.cash.something.netty.im.protocal.response;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * author cash
 * create 2019-01-15-22:58
 **/
@Getter
@Setter
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
