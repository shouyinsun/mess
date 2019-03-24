package com.cash.something.netty.im.protocal.command;

/**
 * 指令
 * author cash
 * create 2019-01-14-22:56
 **/
public interface Command {
    Byte LOGIN_REQUEST =1;
    Byte LOGIN_RESPONSE =2;
    Byte MESSAGE_REQUEST=3;
    Byte MESSAGE_RESPONSE=4;
    Byte LOGOUT_REQUEST = 5;
    Byte LOGOUT_RESPONSE = 6;
    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;
}
