package com.cash.somethingakka.unmodifiable;

import java.util.Collections;
import java.util.List;

/**
 * actor为了安全,传递的对象可以是不可变对象
 * author cash
 * create 2017-08-30-19:50
 **/

public class Message {
    private final int age;
    private final List<String> list;

    public Message(int age, List<String> list){
        this.age = age;
        /**
         * 把普通list包装为不可变对象
         */
        this.list = Collections.unmodifiableList(list);
    }

    public int getAge() {
        return age;
    }

    public List<String> getList() {
        return list;
    }


}
