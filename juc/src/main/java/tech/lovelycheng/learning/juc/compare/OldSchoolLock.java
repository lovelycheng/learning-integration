package tech.lovelycheng.learning.juc.compare;

import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

/**
 * @author chengtong
 * @date 2020/12/16 14:11
 */
public class OldSchoolLock {

    static final Object lock = new Object();
    public static final Runnable WAIT_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            System.err.println(Thread.currentThread().getName() + " get lock");
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.err.println(Thread.currentThread().getName() + " release lock");
        }
    };
    public static final Runnable INPUT_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            System.err.println(Thread.currentThread().getName() + " get lock");
            synchronized (lock) {
                try {
                    System.in.read();
                    lock.notify();
                    lock.notify();
                    lock.notify();
                    lock.notify();
                    lock.notify();
                    lock.notify();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.err.println(Thread.currentThread().getName() + " release lock");
        }
    };
    public static final Runnable INPUT_ALL_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            System.err.println(Thread.currentThread().getName() + " get lock");
            synchronized (lock) {
                try {
                    System.in.read();
                    lock.notifyAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.err.println(Thread.currentThread().getName() + " release lock");
        }
    };
    public static final Runnable LOCK_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            System.err.println(Thread.currentThread().getName() + " get lock");
            synchronized (lock) {

            }
            System.err.println(Thread.currentThread().getName() + " release lock");
        }
    };

    public static final ThreadFactory NAMED_THREAD_FACTORY = new NamedThreadFactory();

    public static void main(String[] args) throws InterruptedException {
        //线程锁的等待队列出入队列的问题
//        lockTest();

        //线程锁的唤醒队列和等待队列的出入队列的问题
//        notifyTest();
        notifyAllTest();

    }



    private static void lockTest() throws InterruptedException {
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(INPUT_TASK);
    }

    private static void notifyTest () throws InterruptedException {

        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(INPUT_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
    }
    private static void notifyAllTest () throws InterruptedException {
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(INPUT_ALL_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
        startTask(LOCK_TASK);
    }

    public static void startTask(Runnable waitTask) throws InterruptedException {
        NAMED_THREAD_FACTORY.newThread(waitTask).start();
        Thread.sleep(100);
    }

}
