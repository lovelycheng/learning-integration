package tech.lovecheng.learing.juc.ThreadPoolExecutorLearning.defaulttest;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chengtong
 * @date 2019/12/11 15:33
 */
public class TPEDefaultTest {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor.DiscardPolicy discardPolicy= new ThreadPoolExecutor.DiscardPolicy();


    }

}
