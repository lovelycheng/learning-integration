package tech.lovelycheng.learning.javalang.jvmtest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.*;

import static java.lang.System.err;

/**
 * @author chengtong
 * @date 2020/4/2 10:41
 */
public class BIASREVOKEDANDREBIASEDTest {

    private static final Unsafe U;
    private static final long OFFSET = 0L;

    static {

        try {
            Field unsafe = Unsafe.class.getDeclaredField("theUnsafe");
            unsafe.setAccessible(true);
            U = (Unsafe) unsafe.get(null);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();

//        Monitor monitor = new Monitor();

//        synchronized (monitor) {

//            err.println("monitor header: " + printBinaryHeader(monitor));
            //reach biasedLockingBulkRebiasThreshold
            Monitor s = new Monitor();

            synchronized (s) {
                err.println("monitor header: " + printBinaryHeader(s));
            }

            threadPoolExecutor.submit(() -> {
                synchronized (s) {
                    err.println("monitor header: " + printBinaryHeader(s));
                }
                return null;
            }).get();

//                if (i == 4) {
//                    monitor.fn();
//                }

//            }


        err.println("monitor header: " + printBinaryHeader(s));

        ss(threadPoolExecutor, s);

        threadPoolExecutor.shutdown();


    }

    private static void ss(ExecutorService threadPoolExecutor, Monitor monitor) throws InterruptedException, ExecutionException {
        threadPoolExecutor.submit(() -> {
            synchronized (monitor) {
                err.println("expect 101 at low end  monitor header: " + printBinaryHeader(monitor));
            }
        }).get();
    }

    private static String printHeader(Object a) {
        int word = U.getInt(a, OFFSET);
        return Integer.toHexString(word);
    }

    private static String printBinaryHeader(Object a) {
        int word = U.getInt(a, OFFSET);
        return Integer.toBinaryString(word);
    }


    private static class Monitor {
        // mutex object

        public void fn() {
        }
    }

}
