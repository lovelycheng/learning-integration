package tech.lovelycheng.learning.javalang.threadlocal;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author chengtong
 * @date 2020/2/17 12:39
 */
public class ThreadLocalDemo {

    public static void main(String[] args) throws Exception {
        getThreadLocal();

        System.gc();

        Thread.sleep(3000L);

        Field map = Thread.class.getDeclaredField("threadLocals");

        map.setAccessible(true);
        Object o = map.get(Thread.currentThread());// thread.threadlocals

        System.out.println(o);

    }

    private static void getThreadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("本地变量");
        System.err.println("threadlocalId:"+threadLocal);
        MyThread e = new MyThread(() -> {
            threadLocal.set("eee");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.gc();
            System.err.println(threadLocal.get());
        });

        System.err.println(threadLocal.get());
        e.start();
    }

}
