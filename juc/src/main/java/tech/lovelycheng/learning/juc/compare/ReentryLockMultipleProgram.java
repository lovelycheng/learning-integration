package tech.lovelycheng.learning.juc.compare;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static tech.lovelycheng.learning.juc.compare.OldSchoolLock.startTask;

/**
 * @author chengtong
 * @date 2020/12/18 15:33
 */
public class ReentryLockMultipleProgram {

    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final Condition condition = reentrantLock.newCondition();
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    private static final Runnable BLOCK_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        reentrantLock.lock();
        try{
            System.err.println(Thread.currentThread().getName() + " get lock");

            System.err.println(Thread.currentThread().getName() + " release lock");
        } finally {
            reentrantLock.unlock();
        }
    };
    private static final Runnable WAIT_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        reentrantLock.lock();
        try{
            System.err.println(Thread.currentThread().getName() + " get lock");
            while(atomicInteger.get() == 0){
                condition.await();
            }
            System.err.println(Thread.currentThread().getName() + " release lock");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    };
    private static final Runnable INPUT_TASK = () -> {
        System.err.println(Thread.currentThread().getName() + " start");
        reentrantLock.lock();
        try{
            System.err.println(Thread.currentThread().getName() + " get lock");
            System.in.read();
            condition.signal();
            condition.signal();
            condition.signal();
            condition.signal();
            atomicInteger.incrementAndGet();
            System.err.println(Thread.currentThread().getName() + " release lock");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    };
    private static final Runnable getLock = () -> {
        System.err.println("contention start");
        int c=0;
        while (c < 100){
            reentrantLock.lock();
            System.err.println("contention get lock");
            reentrantLock.unlock();
            c++;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        test();
    }


    static void test() throws InterruptedException {
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(WAIT_TASK);
        startTask(INPUT_TASK);
        startTask(BLOCK_TASK);
        startTask(BLOCK_TASK);
        startTask(BLOCK_TASK);
        startTask(BLOCK_TASK);
        startTask(getLock);
    }

}
