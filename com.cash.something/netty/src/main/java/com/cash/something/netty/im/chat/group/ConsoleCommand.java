package com.cash.something.netty.im.chat.group;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令
 * author cash
 * create 2019-02-26-23:56
 **/
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
