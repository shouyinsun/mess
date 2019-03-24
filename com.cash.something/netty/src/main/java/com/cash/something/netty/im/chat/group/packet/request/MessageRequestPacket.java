package com.cash.something.netty.im.chat.group.packet.request;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Data;

/**
 * author cash
 * create 2019-02-18-23:15
 **/
@Data
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

    public MessageRequestPacket(){

    }

    public MessageRequestPacket(String toUserId,String message){
        this.toUserId=toUserId;
        this.message=message;
    }
}
