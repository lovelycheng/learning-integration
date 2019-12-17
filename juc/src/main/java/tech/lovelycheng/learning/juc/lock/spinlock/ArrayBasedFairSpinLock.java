package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Anderson’s array lock 的一种实现，同时只能支持N个线程的并发
 *
 * @author chengtong
 * @date 2019/12/17 16:08
 */
public class ArrayBasedFairSpinLock implements SpinLock {

    // 最高支持10根线程并发，并维持内存的可见性
    private volatile long[] array = {1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};

    private AtomicInteger head = new AtomicInteger(0);

    @Override
    public void lock() {
        int slot = head.getAndIncrement();

//        if (slot % array.length == 0 && slot>0) {
//            slot = head.getAndAdd(-array.length);
//        }
        slot = slot % array.length; // 票据是有限的

        System.err.println(Thread.currentThread().getName() + " 获取票据" + slot);

        long v = array[slot];
        while (v != 1L) {
            v = array[slot];
        }

        System.err.println(Thread.currentThread().getName() + " 获取锁，票据" + slot);

        ((WriterThread) Thread.currentThread()).getTicketLocal().set(slot);
    }

    @Override
    public void unlock() {
        int slot = ((WriterThread) Thread.currentThread()).getTicketLocal().get();
        int next_slot = slot+1;
        if (slot == (array.length - 1)) {
            next_slot = 0;
        }
        System.err.println(Thread.currentThread().getName()+" 释放锁，释放票据"+slot);
        array[slot] = 0L;
        array[next_slot] = 1L;
    }
}
