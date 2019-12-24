package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author chengtong
 * @date 2019/12/24 10:18
 */
public class CLHLinkedNodeLock implements SpinLock {

    private ThreadLocal<CLHLinkedNodeLock.Node> nodeThreadLocal = new ThreadLocal<>();

    private Node head = new Node(null,true,Thread.currentThread());
    volatile Node tail = null;


    private static final AtomicReferenceFieldUpdater<CLHLinkedNodeLock, CLHLinkedNodeLock.Node> UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(CLHLinkedNodeLock.class, CLHLinkedNodeLock.Node.class,
                    "tail");

    @Override
    public void lock() {
        Node node = nodeThreadLocal.get();
        if(node == null){
            node = new Node(null,false,Thread.currentThread());
        }

        Node pred = UPDATER.getAndSet(this,node);

        if(pred == null){
            node.pred = head;
        }else {
//            System.err.println(Thread.currentThread().getName() + " 没能获取锁，当前列表索引：" + node + "上一个node：" + pred);
            node.pred = pred;
        }

        boolean done = node.pred.done;

        while(!done){
            done = node.pred.done;
        }

        node.done = false;

        System.err.println(Thread.currentThread().getName() + " 获取锁，当前列表索引：" + node + "上一个node：" + node.pred);

        nodeThreadLocal.set(node);

    }

    @Override
    public void unlock() {

        Node node = nodeThreadLocal.get();
        node.done = true;

        node.pred.own = Thread.currentThread();
        node.pred.done = false;
        nodeThreadLocal.set(node.pred);

        System.err.println(Thread.currentThread().getName() + " 释放锁");
    }

    private static class Node {

        private Node pred;
        private Thread own;
        volatile private boolean done;

        public Node(Node pred, Boolean done,Thread thread) {
            this.pred = pred;
            this.done = done;
            this.own = thread;
        }

        @Override
        public String toString() {
            return "持有线程:"+own.getName()+",done:"+ done ;
        }



    }


}
