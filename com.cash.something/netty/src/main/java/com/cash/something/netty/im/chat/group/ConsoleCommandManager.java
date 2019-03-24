package com.cash.something.netty.im.chat.group;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台命令 管理
 * author cash
 * create 2019-02-27-23:14
 **/
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command=scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if(null!=consoleCommand){
            consoleCommand.exec(scanner,channel);
        }else{
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }

    }
}
