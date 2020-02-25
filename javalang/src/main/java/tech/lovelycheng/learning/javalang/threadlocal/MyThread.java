package tech.lovelycheng.learning.javalang.threadlocal;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author chengtong
 * @date 2020/2/17 12:47
 */
public class MyThread extends Thread{

    MyThread(Runnable runnable){
        super(runnable);
    }




}
