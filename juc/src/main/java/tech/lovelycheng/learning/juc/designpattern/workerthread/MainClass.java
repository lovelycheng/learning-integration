package tech.lovelycheng.learning.juc.designpattern.workerthread;

import java.util.concurrent.CountDownLatch;

/**
 * @author chengtong
 * @date 2020/1/6 13:48
 */
public class MainClass {


    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Channel();

        Client client = new Client(channel);

        client.start();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

}
