package tech.lovelycheng.learning.juc.designpattern.readwritelock;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author chengtong
 * @date 2019/12/16 17:15
 */
public class ReadWriteWithCountLock {

    private AtomicInteger readerCount = new AtomicInteger(0);
    private AtomicInteger writerCount = new AtomicInteger(0);

//    Object readLock = new Object();
//    Object writeLock = new Object();

    public synchronized void lockReadLock() {
        try {
            while (writerCount.get() > 0) {
                System.err.println(Thread.currentThread().getName()+" 读线程阻塞");
                wait();//
                System.err.println(Thread.currentThread().getName()+" 读线程阻塞结束");
            }
        } catch (InterruptedException e) {
            return;
        }
        System.err.println(Thread.currentThread().getName()+" 获取读锁");
        readerCount.getAndIncrement();
        notifyAll();
    }

    public synchronized void unlockReadLock() {
        readerCount.getAndDecrement();
        notifyAll();
        System.err.println(Thread.currentThread().getName()+" 释放读锁");
    }

    public synchronized void lockWriteLock() {
        try {
            while (writerCount.get() > 0 || readerCount.get() > 0) {
                System.err.println(Thread.currentThread().getName()+" 写线程阻塞");
                wait();
                System.err.println(Thread.currentThread().getName()+" 写线程阻塞结束");
            }
        } catch (InterruptedException e) {
            return;
        }
        System.err.println(Thread.currentThread().getName()+" 获取写锁");
        writerCount.getAndIncrement();
        notifyAll();
    }

    public synchronized void unlockWriteLock() {
        writerCount.getAndDecrement();
        System.err.println(Thread.currentThread().getName()+" 释放写锁");
        notifyAll();
    }
}
