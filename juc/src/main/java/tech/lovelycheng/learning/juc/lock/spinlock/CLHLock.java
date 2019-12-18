package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/18 10:22
 */
public class CLHLock implements SpinLock {

    ThreadLocal<Integer> predKey = new ThreadLocal<>();
    private NodeQueue queue = new NodeQueue();

    AtomicInteger index = new AtomicInteger(1);
    AtomicInteger tail = new AtomicInteger(0);

    private int atomicSwap2(int index) {
        int h = tail.get();
        while (!tail.compareAndSet(h, index)) {
            h = tail.get();
        }

        return h;
    }


    @Override
    public void lock() {
        Integer currentIndex = ((WriterThread) Thread.currentThread()).getTicketLocal().get();
        if(currentIndex == null){
            currentIndex = index.getAndIncrement();
        }

        int pred = atomicSwap2(currentIndex);

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
        ((WriterThread) Thread.currentThread()).getTicketLocal().set(pred);
    }

    public class NodeQueue {

        volatile ArrayList<CLHLock.Node> nulls = new ArrayList<>();

        volatile ArrayList<CLHLock.Node> list = new ArrayList<>();

        NodeQueue() {
            for (int i = 0; i < 10000; i++) {
                nulls.add(new CLHLock.Node());
            }
            list.addAll(nulls);
        }

        public CLHLock.Node get(int i) {
            return list.get(i);
        }

    }


    private class Node {

        private int next = 0;
        private boolean done = true;

        Node() {
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }

}
