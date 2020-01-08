package tech.lovelycheng.learning.juc.designpattern.twophasetermination;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chengtong
 * @date 2020/1/8 03:56
 */
public class MyCountDownLatch {

    private Sync sync;

    private static class Sync extends AbstractQueuedSynchronizer {

        final int part;

        Sync(int count, int part) {
            this.part = part;
            this.setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {

            if(getState() > 0){
                return 1;
            }
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int s = getState();
            int update = s - arg;

            if (s == 0) {
                if (compareAndSetState(0, part)) {
                    System.err.println("当前线程:" + Thread.currentThread().getName() + " 开启下一个阶段");
                }
            }

            s = getState();
            update = s - arg;
            while (!compareAndSetState(s, update)) {
                s = getState();
                update = s - arg;
            }

            if (update == 0) {
                System.err.println("当前线程:" + Thread.currentThread().getName() + " 是当前阶段最后一个");
                // TODO: 2020/1/8 如果这里不用 generation之类的东西 会有问题。

                this.acquireShared(1);

                return true;
            }

            return false;
        }
    }

    MyCountDownLatch(int count) {
        this.sync = new Sync(count, count);

    }

    public void countDown() {
        this.sync.releaseShared(1);
    }

    public void await(){
        this.sync.acquireShared(1);
    }
}
