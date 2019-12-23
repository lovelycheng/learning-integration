package tech.lovelycheng.learning.juc.designpattern.readwritelock;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author chengtong
 * @date 2019/12/16 17:15
 *
 * 如果一个线程持有了写锁、那么其他的读线程（reader）和写线程（writer）都会进入wait(),wait 在当前this 的monitorObject的waitSet上
 * consider：
 * 如果持有线程退出，会同时唤醒 reader和writer 那么谁应该获取锁权限？ j.u.c的答案是 writer
 * 如果一个reader尝试获取读锁，此时有writer在等待，reader是否应该获取线程或者还是进入等待队列？
 * 如果一个reader释放读锁，同时有新进入的reader和在等待的writer，谁应该获取锁？
 * writer在获取锁之前是否支持公平获取或者非公平获取，即在writer获取写锁之前被其他的写锁barge？
 * writer之间的公平和writer/reader之间的公平
 *
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
