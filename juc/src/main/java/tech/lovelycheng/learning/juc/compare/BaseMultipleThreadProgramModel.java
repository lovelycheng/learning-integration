package tech.lovelycheng.learning.juc.compare;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengtong
 * @date 2020/12/6 13:37
 */
public class BaseMultipleThreadProgramModel {

    ReentrantLock inputLock = new ReentrantLock();
    Condition notFull = inputLock.newCondition();


    ReentrantLock outputLock = new ReentrantLock();
    Condition notEmpty = outputLock.newCondition();

    private final AtomicInteger num = new AtomicInteger(100);

    /**
     * traditional multi threads program
     * Guard-block
     */
    private void addNum(){
        int c = 0;
        inputLock.lock();
        try{
            while(num.get() == 100){
                notFull.await();
            }
            c = num.incrementAndGet();
            if(c < 100){
                notFull.signal();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            inputLock.unlock();
        }

        if(c > 0){
            try{
                outputLock.lock();
                notEmpty.signal();
            }finally {
                outputLock.unlock();
            }
        }
    }

    /**
     * traditional multi threads program
     * Guard-block
     */
    private void minusNum(){
        int c = -1;
        outputLock.lock();
        try{
            while(num.get() == 0){
                notEmpty.await();
            }
            c = num.addAndGet(-5);
            if(c > 0){
                notEmpty.signal();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            outputLock.unlock();
        }
        if(c < 100){
            try{
                inputLock.lock();
                notFull.signal();
            }finally {
                inputLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BaseMultipleThreadProgramModel model = new BaseMultipleThreadProgramModel();

        Thread t1 = new Thread(model::addNum);
//        Thread t2 = new Thread(model::addNum);
//        Thread t3 = new Thread(model::addNum);

        t1.start();
//        t2.start();
//        t3.start();
        Thread.sleep(1000);
        System.err.println();

        Thread t4 = new Thread(model::minusNum);

        t4.start();
        System.err.println();
    }


}
