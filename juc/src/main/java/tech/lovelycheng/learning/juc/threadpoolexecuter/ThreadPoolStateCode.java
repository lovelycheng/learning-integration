package tech.lovelycheng.learning.juc.threadpoolexecuter;

/**
 * @author chengtong
 * @date 2019/12/8 17:49
 */
public class ThreadPoolStateCode {

    public static void main(String... args) {

        int COUNT_BITS = Integer.SIZE - 3;
        final int RUNNING = -1 << COUNT_BITS;
        final int SHUTDOWN = 0 << COUNT_BITS;
        final int STOP = 1 << COUNT_BITS;
        final int TIDYING = 2 << COUNT_BITS;
        final int TERMINATED = 3 << COUNT_BITS;
        final int cap = (1 << COUNT_BITS) - 1 ;

        System.err.println(COUNT_BITS);
        System.err.println(RUNNING);
        System.err.println(SHUTDOWN);
        System.err.println(STOP);
        System.err.println(TIDYING);
        System.err.println(TERMINATED);
        System.err.println("RUNNING:"+Integer.toBinaryString(RUNNING & ~cap));
        System.err.println("SHUTDOWN:"+Integer.toBinaryString(SHUTDOWN & ~cap));
        System.err.println("STOP:"+Integer.toBinaryString(STOP & ~cap));
        System.err.println("TIDYING:"+Integer.toBinaryString(TIDYING & ~cap));
        System.err.println("TERMINATED:"+Integer.toBinaryString(TERMINATED & ~cap));
        System.err.println("cap:"+Integer.toBinaryString(cap));
        System.err.println("cap:"+cap);

        System.err.println("RUNNING | 0:"+Integer.toBinaryString(RUNNING | 0));
        System.err.println("RUNNING & ~cap:"+Integer.toBinaryString(RUNNING & ~cap));
        System.err.println("~cap:"+Integer.toBinaryString( ~cap));
        int c = ~cap;
        System.err.println("~cap:"+Integer.toBinaryString( c));
        System.err.println(Integer.toBinaryString(RUNNING & cap));

    }


}
