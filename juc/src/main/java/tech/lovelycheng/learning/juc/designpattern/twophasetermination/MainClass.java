package tech.lovelycheng.learning.juc.designpattern.twophasetermination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chengtong
 * @date 2020/1/8 04:20
 */
public class MainClass {

    public static void main(String[] args) {
        MyCountDownLatch cyclicBarrier = new MyCountDownLatch(4) ;

        CountDownLatch countDownLatch = new CountDownLatch(4);

        MainClass.WorkThread workThread1 = new MainClass.WorkThread(4,"thread-1",cyclicBarrier,countDownLatch);
        MainClass.WorkThread workThread2 = new MainClass.WorkThread(4,"thread-2",cyclicBarrier,countDownLatch);
        MainClass.WorkThread workThread3 = new MainClass.WorkThread(4,"thread-3",cyclicBarrier,countDownLatch);
        MainClass.WorkThread workThread4 = new MainClass.WorkThread(4,"thread-4",cyclicBarrier,countDownLatch);

        workThread1.start();
        workThread2.start();
        workThread3.start();
        workThread4.start();
    }

    private static class WorkThread extends Thread {

        final int phase;

        MyCountDownLatch cyclicBarrier;

        CountDownLatch countDownLatch;

        public WorkThread(int phase, String name, MyCountDownLatch cyclicBarrier, CountDownLatch countDownLatch) {
            super(name);
            this.phase = phase;

            this.cyclicBarrier = cyclicBarrier;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            String name = Thread.currentThread().getName();

            for (int i = 0; i < phase; i++) {

                System.err.println(name + "第" + i + "阶段：begin");

                try {
                    Thread.sleep((long) (Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.err.println(name + "第" + i + "阶段：end");
                cyclicBarrier.await();
            }
            countDownLatch.countDown();
        }
    }

}
