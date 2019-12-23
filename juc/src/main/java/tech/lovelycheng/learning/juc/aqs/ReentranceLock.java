package tech.lovelycheng.learning.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengtong
 * @date 2019/12/18 15:06
 */
public class ReentranceLock {


    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.tryLock(2000, TimeUnit.SECONDS);
            reentrantLock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
