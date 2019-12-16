package tech.lovelycheng.learning.juc.threadpoolexecuter;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/9 15:10
 */
public class TPEawaitConfition {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread t = new Thread(r);
                t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
                return t;
            }
        };

        ThreadPoolExecutor poolExecutor = new TPEHookExpend(8, 8, 0, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(10)
                , threadFactory);

        poolExecutor.prestartAllCoreThreads();
        poolExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
                System.err.println("hello world!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        );

//        Thread.sleep(10000);

        // 初始化的线程池中有设置好的8根线程，能够执行任务；

        try {
            System.err.println(poolExecutor);
            poolExecutor.shutdownNow();
            poolExecutor.awaitTermination(10000L, TimeUnit.MILLISECONDS);
            System.err.println(poolExecutor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);

        countDownLatch.await();

    }


}
