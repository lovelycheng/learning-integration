package tech.lovelycheng.learning.juc.designpattern.future;

import java.util.concurrent.*;

/**
 * @author chengtong
 * @date 2020/1/6 15:24
 */
public class Main {

    public static void main(String[] args) {

        Host host = new Host();

        Data data = host.request();

        System.err.println(Thread.currentThread().getName()+" do some other things");
        try {
            Thread.sleep(9000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(Thread.currentThread().getName()+" do some other things end");

        System.err.println(data.getContent());

        futureTask();

    }

    private static void futureTask(){

        Future future = Executors.newCachedThreadPool().submit(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"asda");


        System.err.println(Thread.currentThread().getName()+" do some other things");
        try {
            Thread.sleep(9000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(Thread.currentThread().getName()+" do some other things end");


        try {
            System.err.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
