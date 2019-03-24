package com.cash.something.netty.im.chat.p2p;

import lombok.Data;

/**
 * author cash
 * create 2019-02-24-11:29
 **/
@Data
public class Session {

    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId,String userName) {
        this.userId=userId;
        this.userName=userName;
    }
}
