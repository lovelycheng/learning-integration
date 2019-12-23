package tech.lovelycheng.learning.juc.designpattern.readwritelock;

import com.alibaba.fastjson.JSON;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import util.ReflectionUtil;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chengtong
 * @date 2019/12/16 17:57
 */
public class MainClass {

    public static void main(String[] args) throws IllegalAccessException {
        SharedResource resource = new SharedResourceWithJUC();

        new ReaderThread(resource).start();
        new ReaderThread(resource).start();
        new ReaderThread(resource).start();
        new ReaderThread(resource).start();

        new WriterThread(resource).start();
        new WriterThread(resource).start();
//        new WriterThread(resource).start();
//
        ReentrantReadWriteLock lock = (ReentrantReadWriteLock)ReflectionUtil.getField("readWriteLock",resource,SharedResourceWithJUC.class);

        System.err.println(JSON.toJSONString(lock));

    }

}
