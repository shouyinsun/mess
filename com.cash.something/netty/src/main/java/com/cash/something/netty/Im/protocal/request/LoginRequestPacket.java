package com.cash.something.netty.im.protocal.request;

import com.cash.something.netty.im.protocal.Packet;
import com.cash.something.netty.im.protocal.command.Command;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录指令包
 * author cash
 * create 2019-01-14-22:57
 **/
@Getter
@Setter
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String userName;

    private String passWord;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
