package tech.lovelycheng.learning.juc.designpattern.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chengtong
 * @date 2019/12/16 21:40
 */
public class SharedResourceWithJUC extends SharedResource{

    String content = "*";

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();



    @Override
    public String read(){
        readLock.lock();
        try {
            return content;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void write(String newContent){
        writeLock.lock();
        System.err.println(Thread.currentThread().getName()+" 获取写锁");
        StringBuffer stringBuffer = new StringBuffer(content);
        stringBuffer.append(newContent);
        this.content = stringBuffer.toString();
        System.err.println(Thread.currentThread().getName()+" 释放写锁");
        writeLock.unlock();
    }





}
