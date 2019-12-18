package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/18 10:22
 *
 * 一个基本的clh lock的实现
 * 不需要在exit的时候自旋、
 * 等上一个完成、复用node
 *
 *
 */
public class CLHSpinLock implements SpinLock {

    private ThreadLocal<Integer> predKey = new ThreadLocal<>();
    private NodeQueue queue = new NodeQueue();

    private AtomicInteger index = new AtomicInteger(1);
    private AtomicInteger tail = new AtomicInteger(0);

    @Override
    public void lock() {
        Integer currentIndex = ((WriterThread) Thread.currentThread()).getTicketLocal().get();
        if(currentIndex == null){
            currentIndex = index.getAndIncrement();
        }

        int pred;
        do{
            pred = tail.get();
        }while (!tail.compareAndSet(pred,currentIndex));

        Node mynode = queue.get(currentIndex);

        mynode.setDone(false);

        Node predNode = queue.get(pred);
        while (!predNode.isDone()) {
            predNode = queue.get(pred);
        }

        System.err.println(Thread.currentThread().getName() + " 获取锁，当前node 索引：" + currentIndex + "上一个node:" + pred);
        predKey.set(pred);
        ((WriterThread) Thread.currentThread()).getTicketLocal().set(currentIndex);
    }

    @Override
    public void unlock() {
        int currentIndex = ((WriterThread) Thread.currentThread()).getTicketLocal().get();
        queue.get(currentIndex).setDone(true);
        int  pred = predKey.get();
        System.err.println(Thread.currentThread().getName() + " 释放锁，当前列表index：" + currentIndex);
        /*
         * 这一步是 复用 node
         */
        ((WriterThread) Thread.currentThread()).getTicketLocal().set(pred);
    }

    public static class NodeQueue {

        volatile ArrayList<CLHSpinLock.Node> nulls = new ArrayList<>();

        volatile ArrayList<CLHSpinLock.Node> list = new ArrayList<>();

        NodeQueue() {
            for (int i = 0; i < 10000; i++) {//足够的nodes
                nulls.add(new Node());
            }
            list.addAll(nulls);
        }

        public CLHSpinLock.Node get(int i) {
            return list.get(i);
        }

    }


    private static class Node {

        private boolean done = true; // String state = "done" || "wait" ;效果其实是一样的

        Node() {
        }

        boolean isDone() {
            return done;
        }

        void setDone(boolean done) {
            this.done = done;
        }
    }

}
