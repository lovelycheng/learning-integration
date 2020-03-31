package tech.lovelycheng.learning.javalang.jvmtest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        for (int i = 0; i < 25; i++) {
//            boolean expectBiasAbleAndRevoke = i<9;
//            boolean expectBiasLockReBias = i==9;
//            boolean expectBiasAbleAndRevoke2 = i>9 && i<19;
//            boolean expectBiasLockForbidden = i==19;
//            boolean expectClassUnBias = i>19;

            System.err.println("========================================index = "+ i + "========================================");
            final Monitor a = new Monitor();
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
        }


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