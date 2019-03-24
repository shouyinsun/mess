package com.cash.something.netty.im.chat.optimize.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.cash.something.netty.im.chat.optimize.protocol.Packet;

import static com.cash.something.netty.im.chat.optimize.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
