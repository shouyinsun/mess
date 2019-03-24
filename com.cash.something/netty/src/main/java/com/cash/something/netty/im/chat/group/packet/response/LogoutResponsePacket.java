package com.cash.something.netty.im.chat.group.packet.response;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Data;

/**
 * author cash
 * create 2019-02-27-23:26
 **/
@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
