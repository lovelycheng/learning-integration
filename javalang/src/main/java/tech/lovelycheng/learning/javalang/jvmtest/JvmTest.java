package tech.lovelycheng.learning.javalang.jvmtest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.err;
import static java.lang.System.out;

public class JvmTest {

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


    public static void main(String[] args) throws Exception {

        ExecutorService thread = Executors.newSingleThreadExecutor();

        System.err.println("before test ");

        Monitor revokeAndBiasTest = new Monitor();

        synchronized (revokeAndBiasTest){

        }

        Monitor[] monitors = new Monitor[25];

        for (int i = 0; i < 25; i++) {
            System.err.println("========================================index = "+ i + "========================================");
            final Monitor a = new Monitor();
            monitors[i] = a;
            synchronized (a) {
                out.println("Main thread :0x" + printHeader(a));
                out.println("a markword " + printBinaryHeader(a));
            }
            System.out.println("================================================================================");
            if(i == 10){
                Thread.sleep(25001);
                out.println("-------- sleeped for 25001 --------");
            }
            thread.submit(() -> {
                synchronized (a) {
                    out.println("Work thread :0x" + printHeader(a));
                    out.println("a markword " + printBinaryHeader(a));
                }
                return null;
            }).get();
            if(i==9){
                synchronized (monitors[0]){
                    err.println("main thread reget monitor[0],with lock " + printBinaryHeader(monitors[0]));
                }
            }
        }

        err.println("-----------revokeAndBiasTest-------------");
        thread.submit(() -> {
            synchronized (revokeAndBiasTest) {
                out.println("Work thread :0x" + printHeader(revokeAndBiasTest));
                out.println("a markword " + printBinaryHeader(revokeAndBiasTest));
            }
            return null;
        }).get();

        thread.shutdown();
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
    }

}