package com.cash.spring.aop.plugin;

import com.cash.spring.aop.plugin.utils.ReflectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ReflectionUtils测试
 * author cash
 * create 2017-10-13-10:53
 **/

public class ReflectionUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtilsTest.class);

    @Test
    public void test(){
        //TestGetAndSetFieldValue();
        TestGetAndSetProperty();
    }

    public void TestGetAndSetFieldValue(){
        TstObj tstObj=new TstObj("423531234123",false,"cash",20,new String[]{"code","code"});
        Object value= ReflectionUtils.getFieldValue(tstObj,"idCardNo");
        System.out.println("idCardNo: "+value);

        ReflectionUtils.setFieldValue(tstObj,"idCardNo","23412334512341234");
        value= ReflectionUtils.getFieldValue(tstObj,"idCardNo");
        System.out.println("idCardNo: "+value);

    }


    public void TestGetAndSetProperty(){//setter跟getter方法
        TstObj tstObj=new TstObj("423531234123",false,"cash",20,new String[]{"code","code"});
        Object v1=ReflectionUtils.getProperty(tstObj,"foreign");
        System.out.println("v1: "+v1);

        tstObj.setIdCardNo("2354132512345123");
        Object v2=ReflectionUtils.getProperty(tstObj,"idCardNo");
        System.out.println("v2: "+v2);

        tstObj.setForeign(true);
        Object v3=ReflectionUtils.getProperty(tstObj,"foreign");
        System.out.println("v3: "+v3);

    }


}
