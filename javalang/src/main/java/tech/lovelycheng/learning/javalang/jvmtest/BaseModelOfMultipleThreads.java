package tech.lovelycheng.learning.javalang.jvmtest;

/**
 * @author chengtong
 * @date 2020/11/20 15:21
 */
public class BaseModelOfMultipleThreads {

    static Object lock =new Object();
    static volatile boolean condition = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock){
                while(!condition){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1 is running");
                condition = !condition;
                lock.notify();
            }
        }).start();

        new Thread((() -> {
            synchronized (lock){
                while(condition){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 is running");
                condition = !condition;
                lock.notify();
            }
        })).start();
    }
}
