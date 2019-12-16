package tech.lovelycheng.learning.lang;

/**
 * @author chengtong
 * @date 2019/12/13 10:53
 */
public class ThreadTest {

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("hello world");
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
