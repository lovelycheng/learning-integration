package tech.lovelycheng.learning.juc.threadpoolexecuter.workerlifecycle;

import util.ReflectionUtil;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/10 14:54
 */
public class LifeCycleTest {

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
//        spikePreStartCoreThread();
        testBetweenCoreSizeAndMaxSize();
    }

    /**
     * preStartCoreThread
     */
    private static void spikePreStartCoreThread() throws IllegalAccessException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
            return t;
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory);

        AtomicInteger ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        HashSet workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+0);
        System.err.println("workers:"+workers.size()+",expect:"+0);

        poolExecutor.prestartCoreThread();

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+1);
        System.err.println("workers:"+workers.size()+",expect:"+1);

        poolExecutor.prestartCoreThread();

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+2);
        System.err.println("workers:"+workers.size()+",expect:"+2);


//        poolExecutor.allowCoreThreadTimeOut(true);

//        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
//        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
//        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+0);
//        System.err.println("workers:"+workers.size()+",expect:"+0);

        Thread.sleep(3000);

        Runnable neverRelease = () -> {
            try {
                System.err.println(Thread.currentThread().getName()+"expect to print");
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        poolExecutor.execute(neverRelease);

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+3);
        System.err.println("workers:"+workers.size()+",expect:"+3);

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

    }

    private static void testBetweenCoreSizeAndMaxSize() throws IllegalAccessException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
            return t;
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), threadFactory);

        AtomicInteger ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        HashSet workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+0);
        System.err.println("workers:"+workers.size()+",expect:"+0);

        // five times fill coreSize thread
        poolExecutor.prestartCoreThread();
        poolExecutor.prestartCoreThread();
        poolExecutor.prestartCoreThread();
        poolExecutor.prestartCoreThread();
        poolExecutor.prestartCoreThread();

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+5);
        System.err.println("workers:"+workers.size()+",expect:"+5);


        Runnable neverRelease = () -> {
            try {
                System.err.println(Thread.currentThread().getName()+" expect to print");
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // fill core thread
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);

        // fill workQueue
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);

        // fill another five thread to reach maxSize
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);
        poolExecutor.execute(neverRelease);


        // 这些个任务会被执行..

        ctl = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        ArrayBlockingQueue<Runnable> workQueue = (ArrayBlockingQueue) ReflectionUtil.getField("workQueue",poolExecutor,
                ThreadPoolExecutor.class);
        workers = (HashSet) ReflectionUtil.getField("workers",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+ReflectionUtil.workerCountOf(ctl.get())+",expect:"+10);
        System.err.println("workers:"+workers.size()+",expect:"+10);
        System.err.println("workQueue:"+workQueue.size()+",expect:"+10);


//        poolExecutor.execute(neverRelease); // expect 报错

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

    }


}
