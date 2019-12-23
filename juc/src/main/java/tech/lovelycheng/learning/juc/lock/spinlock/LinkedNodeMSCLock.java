package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author chengtong
 * @date 2019/12/23 16:24
 */
public class LinkedNodeMSCLock implements SpinLock{

    private ThreadLocal<Node> nodeThreadLocal = new ThreadLocal<>();

    volatile Node tail = null;

    private static final AtomicReferenceFieldUpdater<LinkedNodeMSCLock, Node> UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(LinkedNodeMSCLock.class, Node.class,
                    "tail");


    @Override
    public void lock() {

        Node node = new Node(null,true, Thread.currentThread());

        Node predNode = UPDATER.getAndSet(this,node);

        if(predNode != null){
            // another thread already queued
            System.err.println(Thread.currentThread().getName() + " 没能获取锁，当前列表索引：" + node + "上一个node：" + predNode);

            predNode.next = node;

            boolean wait;

            do {
                wait = node.wait;
            }while (wait);

        }

        node.wait = false;
        nodeThreadLocal.set(node);
        System.err.println(Thread.currentThread().getName() + " 获取锁");

    }

    @Override
    public void unlock() {

        Node node = nodeThreadLocal.get();

        if(node.next == null){
            if(UPDATER.compareAndSet(this,node,null)){
                System.err.println(Thread.currentThread().getName() + "没有等待队列，释放锁");
                node.wait = true;
                return;
            }

            Node next = nodeThreadLocal.get().next;

            while(next == null){

                next = nodeThreadLocal.get().next;
            }
            System.err.println(Thread.currentThread().getName() + " 释放锁,next:"+next);
            node.wait = true;
            next.wait =false;
        }

        node.next.wait = false;
        nodeThreadLocal.remove();
        System.err.println(Thread.currentThread().getName() + " 释放锁");

    }


    private static class Node{

        private volatile Node next;
        private volatile boolean wait;
        private Thread own;

        public boolean isWait() {
            return wait;
        }

        public void setWait(boolean wait) {
            this.wait = wait;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        Node(Node next, boolean wait, Thread own) {
            this.next = next;
            this.wait = wait;
            this.own = own;
        }

        @Override
        public String toString() {
            return "持有线程:"+own.getName()+",wait:"+wait + ",next:"+next;
        }
    }



}
