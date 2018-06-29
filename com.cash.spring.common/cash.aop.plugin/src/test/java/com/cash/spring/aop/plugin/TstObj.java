package com.cash.spring.aop.plugin;

import java.util.Arrays;

/**
 * author cash
 * create 2017-10-13-11:11
 **/

public class TstObj extends TstObjSup {

    private String name;

    private Integer age;

    private String[] hobbies;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public TstObj(String idCardNo, boolean foreign, String name, Integer age, String[] hobbies) {
        super(idCardNo, foreign);
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "TstObj{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobbies=" + Arrays.toString(hobbies) +
                '}';
    }
}
