package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公平自旋锁的实现
 *
 * @author chengtong
 * @date 2019/12/17 14:51
 */
class SpinLockWithTicket implements SpinLock{

    /**
     * 线程持有的票据、每增加一个线程加一。通过cas实现
     */
    private AtomicInteger next = new AtomicInteger(0);

    /**
     * 当前持有锁的、或者是应该持有锁的线程的票据
     */
    private AtomicInteger grand = new AtomicInteger(0);

    @Override
    public void lock() {
        //当前线程获取票据
        int ticket = next.getAndIncrement();
        System.err.println(Thread.currentThread().getName()+" 获取票据"+ticket);

        int now = grand.get();
        //线程在此忙等待
        while(ticket != now){
            now = grand.get();
        }
        // get lock
        System.err.println(Thread.currentThread().getName()+" 获取锁，票据"+ticket);

        ((WriterThread)Thread.currentThread()).getTicketLocal().set(ticket);


    }

    @Override
    public void unlock(){
        int tTicket = ((WriterThread)Thread.currentThread()).getTicketLocal().get();
        if(grand.get() != tTicket){
            System.err.println(Thread.currentThread().getName()+" 持有票据错误");
            return;
        }
        System.err.println(Thread.currentThread().getName()+" 释放锁，释放票据"+tTicket);
        grand.getAndIncrement();
    }



}
