package tech.lovelycheng.learning.javalang.jvmtest;

import java.util.concurrent.*;

/**
 * @author chengtong
 * @date 2020/3/31 20:05
 */
public class TestSyncContention {

    public static void main(String[] args){

        ExecutorService thread = Executors.newSingleThreadExecutor();

        Monitor a = new Monitor();

        try {
            thread.submit(() -> {
                synchronized (a){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception ignore) {
        }

        synchronized (a){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        thread.shutdown();






    }







    private static class Monitor {
        // mutex object
    }

}
