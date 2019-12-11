package tech.lovecheng.learing.juc.ThreadPoolExecutorLearning.statetest;

import util.ReflectionUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/11 19:11
 */
public class StateTransferTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;


    public static void main(String[] args) throws IllegalAccessException, InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setName(String.format("num-%d", atomicInteger.getAndAdd(1)));
            return t;
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(10), threadFactory);

        AtomicInteger ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        int rs = ReflectionUtil.runStateOf(ctl.get());

        System.err.println(rs == RUNNING);// expect true
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable neverRelease = () -> {
            try {
                System.err.println(Thread.currentThread().getName()+" expect to print");
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        };
        poolExecutor.execute(neverRelease); //放入workqueue，放入就被临时线程get,workCount>0阻止了线程池从shutdown 到 terminated
        poolExecutor.execute(neverRelease); //放入workqueue

        poolExecutor.shutdown();
        ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        rs = ReflectionUtil.runStateOf(ctl.get());
        System.err.println(rs == SHUTDOWN); // expect true


        ArrayBlockingQueue<Runnable> workQueue = (ArrayBlockingQueue) ReflectionUtil.getField("workQueue",poolExecutor,
                ThreadPoolExecutor.class);
        System.err.println("workQueue:"+workQueue.size()+",expect:"+1);
        poolExecutor.shutdownNow(); //清空workQueue
        ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        rs = ReflectionUtil.runStateOf(ctl.get());
        System.err.println(rs == STOP);

        countDownLatch.await();
        poolExecutor.shutdownNow(); //清空workQueue
        workQueue = (ArrayBlockingQueue) ReflectionUtil.getField("workQueue",poolExecutor,
                ThreadPoolExecutor.class);
        System.err.println("workQueue:"+workQueue.size()+",expect:"+0);
        ctl = (AtomicInteger) ReflectionUtil.getField("ctl", poolExecutor, ThreadPoolExecutor.class);
        rs = ReflectionUtil.runStateOf(ctl.get());
        System.err.println(rs == TERMINATED);



        System.err.println(rs == TIDYING); // 这个是一个中间状态、在terminate之前先会变成这个状态


    }
}
