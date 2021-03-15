package tech.lovelycheng.learning.juc.compare;

import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chengtong
 * @date 2020/12/24 15:25
 */
public class SharedLockTEST {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static final ThreadFactory NAMED_THREAD_FACTORY = new NamedThreadFactory();
    static int readCount = 0;
    static Runnable ACQUIRE_READ_LOCK = () ->{
        lock.readLock().lock();
        lock.writeLock().lock();

        try{
            readCount++;
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    };

    public static void main(String[] args) {

        NAMED_THREAD_FACTORY.newThread(ACQUIRE_READ_LOCK).start();
        NAMED_THREAD_FACTORY.newThread(ACQUIRE_READ_LOCK).start();
        NAMED_THREAD_FACTORY.newThread(ACQUIRE_READ_LOCK).start();




    }
}
