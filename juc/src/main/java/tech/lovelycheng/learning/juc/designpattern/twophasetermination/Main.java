package tech.lovelycheng.learning.juc.designpattern.twophasetermination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chengtong
 * @date 2020/1/7 15:33
 */
public class Main {


    public static void main(String[] args) {

        System.err.println(Thread.currentThread().isInterrupted());

        Thread.currentThread().interrupt();

        System.err.println(Thread.currentThread().isInterrupted());

        Thread.currentThread().interrupt();

        System.err.println(Thread.currentThread().isInterrupted());

        boolean us = Thread.interrupted();

        System.err.println(Thread.currentThread().isInterrupted());
        System.err.println(us);

        us = Thread.interrupted();

        System.err.println(Thread.currentThread().isInterrupted());
        System.err.println(us);

        Thread t= new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000L);
                    System.err.println("thread is doing something");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            System.err.println("thread is interrupted");

        });
        t.start();

        multiPhaseThread();

    }


    private static void multiPhaseThread(){

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.err.println("CyclicBarrier action");
            }
        });

        CountDownLatch countDownLatch = new CountDownLatch(4);

        WorkThread workThread1 = new WorkThread(4,"thread-1",cyclicBarrier,countDownLatch);
        WorkThread workThread2 = new WorkThread(4,"thread-2",cyclicBarrier,countDownLatch);
        WorkThread workThread3 = new WorkThread(4,"thread-3",cyclicBarrier,countDownLatch);
        WorkThread workThread4 = new WorkThread(4,"thread-4",cyclicBarrier,countDownLatch);

        workThread1.start();
        workThread2.start();
        workThread3.start();
        workThread4.start();


    }


    private static class WorkThread extends Thread{

        final int phase;

        CyclicBarrier cyclicBarrier;

        CountDownLatch countDownLatch;

        public WorkThread(int phase, String name, CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch) {
            super(name);
            this.phase = phase;

            this.cyclicBarrier = cyclicBarrier;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            String name = Thread.currentThread().getName();

            for(int i=0;i<phase;i++){

                System.err.println(name + "第"+i+"阶段：begin");

                try {
                    Thread.sleep((long)(Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.err.println(name + "第"+i+"阶段：end");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch.countDown();
        }
    }

}
