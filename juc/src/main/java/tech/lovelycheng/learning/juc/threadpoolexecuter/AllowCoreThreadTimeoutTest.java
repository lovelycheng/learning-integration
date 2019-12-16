package tech.lovelycheng.learning.juc.threadpoolexecuter;

import com.sun.istack.internal.NotNull;
import util.ReflectionUtil;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/9 21:31
 */
public class AllowCoreThreadTimeoutTest {

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread t = new Thread(r);
                t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
                return t;
            }
        };

        ThreadPoolExecutor poolExecutor = new TPEHookExpend(8, 8, 24, TimeUnit.HOURS, new ArrayBlockingQueue<>(10)
                , threadFactory);

        poolExecutor.prestartAllCoreThreads();// 初始化coreSize个线程；
        poolExecutor.execute(() -> {
                    System.err.println(Thread.currentThread().getName() + " hello world!");
                }
        );

        AtomicInteger ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        HashSet workers = (HashSet) ReflectionUtil.getField("workers", poolExecutor, ThreadPoolExecutor.class);
        System.err.println("workerCount:" + ReflectionUtil.workerCountOf(ctl.get()) + ",expect:" + 8);
        System.err.println("workers:" + workers.size() + ",expect:" + 8);

        System.err.println(poolExecutor);
        Thread.sleep(1000);
        poolExecutor.allowCoreThreadTimeOut(true); // interrupt all threads
        System.err.println(poolExecutor);

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers", poolExecutor, ThreadPoolExecutor.class);
        System.err.println("workerCount:" + ReflectionUtil.workerCountOf(ctl.get()) + ",expect:" + 0);
        System.err.println("workers:" + workers.size() + ",expect:" + 0);
        Thread.sleep(10000);

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers", poolExecutor, ThreadPoolExecutor.class);
        System.err.println("workerCount:" + ReflectionUtil.workerCountOf(ctl.get()) + ",expect:" + 0);
        System.err.println("workers:" + workers.size() + ",expect:" + 0);


        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

    }

}
