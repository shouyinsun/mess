package com.cash.something.netty.im.chat.group;

import com.cash.something.netty.im.chat.group.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * author cash
 * create 2019-02-27-23:30
 **/
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.print("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassWord("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
