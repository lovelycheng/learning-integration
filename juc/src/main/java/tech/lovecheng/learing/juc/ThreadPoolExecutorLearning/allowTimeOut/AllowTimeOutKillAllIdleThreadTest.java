package tech.lovecheng.learing.juc.ThreadPoolExecutorLearning.allowTimeOut;

import com.alibaba.fastjson.JSON;
import com.sun.istack.internal.NotNull;
import tech.lovecheng.learing.juc.ThreadPoolExecutorLearning.TPEHookExpend;
import util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/10 09:06
 */
public class AllowTimeOutKillAllIdleThreadTest {

    public static void main(String[] args) throws InterruptedException {

        try {
            shortKeepAliveTimeCauseThreadsAllKilled();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置了足够短的keepAliveTime，反射获取 workerCount和workers;在足量的任务添加之后线程池初始化了所有核心线程
     * 然后将线程池的allowCoreThreadTimeOut设置成true，所有线程和worker都被干掉，在重新接受任务时,线程池重新添加新的worker
     *
     * @throws InterruptedException
     * @throws IllegalAccessException
     */
    private static void shortKeepAliveTimeCauseThreadsAllKilled() throws InterruptedException, IllegalAccessException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
            return t;
        };

        ThreadPoolExecutor poolExecutor = new TPEHookExpend(8, 8, 1, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(10)
                , threadFactory);

        poolExecutor.prestartAllCoreThreads();// 初始化coreSize个线程；
        for(int i=0;i<10;i++){
            poolExecutor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println(Thread.currentThread().getName() + " hello world!");
                    }
            );
        }
        AtomicInteger c = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:" + workerCountOf(c.get()) + " expect 8");

        HashSet<Object> o =  (HashSet<Object>)ReflectionUtil.getField("workers",poolExecutor,
                ThreadPoolExecutor.class);
        for(Object o1:o.toArray()){
            System.err.println(o1);
        }

        System.err.println(poolExecutor);
        Thread.sleep(13000);
        poolExecutor.allowCoreThreadTimeOut(true);
        // expect to interrupt all idle threads; activeThread => 0
        //but how keepAliveTime make change?
        System.err.println(poolExecutor);
        System.err.println("已创建的线程个数"+atomicInteger.get()+" expect 8");

        CountDownLatch countDownLatch = new CountDownLatch(1);

        poolExecutor.execute(() -> {
                    countDownLatch.countDown();
                    System.err.println(Thread.currentThread().getName() + " hello world!");
                }
        );
        countDownLatch.await();
        c = (AtomicInteger) ReflectionUtil.getField("ctl",poolExecutor,ThreadPoolExecutor.class);
        System.err.println("workerCount:"+workerCountOf(c.get())+"expect 0");

        o =  (HashSet<Object>)ReflectionUtil.getField("workers",poolExecutor,
                ThreadPoolExecutor.class);
        for(Object o1:o.toArray()){
            System.err.println(o1);
        }

        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        countDownLatch1.await();
    }
    private static int workerCountOf(int c)  { return c & ((1 << 29) - 1); };


}
