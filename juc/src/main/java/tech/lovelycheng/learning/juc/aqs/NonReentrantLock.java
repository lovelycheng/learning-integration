package tech.lovelycheng.learning.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author chengtong
 * @date 2019/12/19 10:04
 */
public class NonReentrantLock implements Lock {

    private QueuedSynchronizer sync = new QueuedSynchronizer();

    @Override
    public void lock() {
        sync.tryAcquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
