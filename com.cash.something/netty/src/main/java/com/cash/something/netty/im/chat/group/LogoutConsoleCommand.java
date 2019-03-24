package com.cash.something.netty.im.chat.group;

import com.cash.something.netty.im.chat.group.packet.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * author cash 登出
 * create 2019-02-27-23:16
 **/
public class LogoutConsoleCommand implements ConsoleCommand {
        @Override
        public void exec(Scanner scanner, Channel channel) {
            LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
            channel.writeAndFlush(logoutRequestPacket);
        }

}
