package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.Random;

/**
 * @author chengtong
 * @date 2019/12/17 15:09
 */
public class WriterThread extends Thread{

    private final Random random = new Random(1000);

    private final SpinLockChannel spinLockChannel;

    private ThreadLocal<Integer> ticketLocal = new ThreadLocal<>();

    public ThreadLocal<Integer> getTicketLocal() {
        return ticketLocal;
    }

    public void setTicketLocal(ThreadLocal<Integer> ticketLocal) {
        this.ticketLocal = ticketLocal;
    }

    public WriterThread(String name, SpinLockChannel spinLockChannel) {
        this.spinLockChannel = spinLockChannel;
        this.setName(name);
    }


    @Override
    public void run() {

        for(int i=0;i<1000;i++){
            try {
//                long time = random.nextLong();
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            spinLockChannel.write(currentThread().getName());

        }

    }
}
