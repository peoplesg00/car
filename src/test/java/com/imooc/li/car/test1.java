package com.imooc.li.car;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test1{
    public static void main(String[] args) throws IOException {
//        BASE64Encoder encoder = new BASE64Encoder();
//        BASE64Decoder decoder = new BASE64Decoder();
//        String s1 = "5aW95aSa6ZKx";
//        String s2 = "123456";
//        System.out.println(new String(decoder.decodeBuffer(s1), "utf-8"));
//        System.out.println(new String(encoder.encode(s2.getBytes("utf-8"))));
//        System.out.println(!StringUtils.equals("1", "1"));
        ThreadTest1 test1 =new ThreadTest1("线程1");
        ThreadTest1 test2 =new ThreadTest1("线程2");
        test1.start();
        test2.start();
        System.out.println("主线程结束");
    }
}

class ThreadTest1 extends Thread{
    public ThreadTest1(String s) {
        super(s);
    }
    public void run(){
        for (int i = 1;i<=10;i++){
            System.out.println(Thread.currentThread().getName()+"打印"+i);
        }
        System.out.println(Thread.currentThread().getName()+"结束了");
    }
}
