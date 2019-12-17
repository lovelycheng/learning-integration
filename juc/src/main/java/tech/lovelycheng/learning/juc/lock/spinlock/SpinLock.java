package tech.lovelycheng.learning.juc.lock.spinlock;

/**
 * 自旋锁
 * @author chengtong
 * @date 2019/12/17 15:47
 */
public interface SpinLock {

    void lock();

    void unlock();

}
