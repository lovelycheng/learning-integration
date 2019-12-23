package tech.lovelycheng.learning.juc.lock.spinlock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author chengtong
 * @date 2019/12/17 17:53
 * implement MSC lock
 * <p>
 * Each process has its own “node”.
 * – Spins only on its own node, locally.
 * – Others may write its node.
 * Small space requirements.
 * – Can “reuse” nodes for different locks.
 * – Space overhead: O(L+N), for L locks and N processes,
 * assuming each process accesses only one lock at a
 * time. Can allocate nodes as needed (typically upon process creation).
 * May spin on exit.
 * </p>
 */
public class MSCLockSpinLock implements SpinLock {

    private volatile NodeQueue nodes = new NodeQueue();

    private AtomicInteger currentIndex = new AtomicInteger(1);
    /**
     * 本身的意义是：
     * 有没有已经持有锁的线程 非0 就是有
     * 如果有的话，这个值是持有线程或者是先进入等待序列的上一个线程对应的的node索引
     */
    volatile AtomicInteger tail = new AtomicInteger(0);


//    /**
//     * 这个updater是实现方法 原子级别的swap（更换数据并且返回原值）；
//     *
//     */
//    private static final AtomicReferenceFieldUpdater<MSCLockSpinLock,AtomicInteger> UPDATER =
//            AtomicReferenceFieldUpdater.newUpdater(MSCLockSpinLock.class, AtomicInteger.class,
//            "tail");
//
//
//    private int atomicSwap(int index) {
//        return UPDATER.getAndSet(this,new AtomicInteger(index)).get();
//    }


    /**
     * 这个atomicSwap/atomicSwap2相当于入列的操作，唯一就行，index是getAndIncrement保证唯一性了，不需要UPDATER也行
     * @param index
     * @return
     */
    private int atomicSwap2(int index) {
        int h = tail.get();
        while (!tail.compareAndSet(h,index)){
            h = tail.get();
        }

        return h;
    }


    @Override
    public void lock() {
        int index = currentIndex.getAndIncrement();
        int holdLockIndex = atomicSwap2(index);
        Node node = nodes.get(index);
        if (holdLockIndex != 0) {

            node.setWait(true);
            nodes.get(holdLockIndex).setNext(index);

            System.err.println(Thread.currentThread().getName() + " 没能获取锁，当前列表索引：" + index + "上一个索引：" + holdLockIndex);

            node = nodes.get(index);
            boolean isWait = node.isWait();

            while (isWait) {
                node = nodes.get(index);
                isWait = node.isWait();
            }
        }
        node = nodes.get(index);
        node.setWait(false);


        ((WriterThread) Thread.currentThread()).getTicketLocal().set(index);
        // 获取了锁
        System.err.println(Thread.currentThread().getName() + " 获取锁，当前列表index：" + index);

    }

    @Override
    public void unlock() {
        int index = ((WriterThread) Thread.currentThread()).getTicketLocal().get();
        Node holder = nodes.get(index);
        int nextIndex = holder.getNext();

        System.err.println(Thread.currentThread().getName() + " 释放锁，当前列表index：" + index + "下一个线程索引：" + nextIndex);

        if (holder.getNext() == 0) {
            if (tail.compareAndSet(index, 0)) {
                System.err.println(Thread.currentThread().getName() + "没有竞争将tail改回0");
                return;
            }

            holder = nodes.get(index);
            nextIndex = holder.getNext();
            while (nextIndex == 0) {
                holder = nodes.get(index);
                nextIndex = holder.getNext();
            }
        }
        holder.setWait(true);
        holder.setNext(0);


        Node next = nodes.get(nextIndex);
        next.setWait(false);

    }

    public class NodeQueue {

        volatile ArrayList<Node> nulls = new ArrayList<>();

        volatile ArrayList<Node> list = new ArrayList<>();

        NodeQueue() {
            for (int i = 0; i < 10000; i++) {
                nulls.add(new Node());
            }
            list.addAll(nulls);
        }

        public Node get(int i) {
            return list.get(i);
        }

    }


    private class Node {

        private int next = 0;
        private boolean wait = true;

        Node() {
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public boolean isWait() {
            return wait;
        }

        public void setWait(boolean wait) {
            this.wait = wait;
        }
    }


}
