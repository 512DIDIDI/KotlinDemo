package com.dididi.kotlindemo;

import java.io.Serializable;

/**
 * @author dididi(yechao)
 * @describe
 * @since 23/08/2019
 */

public class HelloWorld implements Serializable {

    private int m = 123;
    private static int x = 10;
    private static final int y = 20;



    public int increace(){
        return m+1;
    }

    public void m() throws Exception{
        // 具体逻辑不写
    }

    public static String hello(){
        return "hello word";
    }

    void helloWorld(){}
}
