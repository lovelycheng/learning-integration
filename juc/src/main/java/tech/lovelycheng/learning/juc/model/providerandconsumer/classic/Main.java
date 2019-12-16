package tech.lovelycheng.learning.juc.model.providerandconsumer.classic;

import java.util.concurrent.CountDownLatch;

/**
 * @author chengtong
 * @date 2019/12/13 15:25
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();

        Thread producer = new Thread(new Producer(room));
        Thread producer1 = new Thread(new Producer(room));
        Thread producer2 = new Thread(new Producer(room));
        Thread consumer = new Thread(new Consumer(room));

        producer.setName("producer");
        producer1.setName("producer1");
        producer2.setName("producer2");


        producer.start();
        producer1.start();
        producer2.start();
        consumer.start();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

}
