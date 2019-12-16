package tech.lovelycheng.learning.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chengtong
 * @date 2019/12/12 14:42
 */
public class QueuedSynchronizer extends AbstractQueuedSynchronizer {

    volatile int c;

    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }

    @Override
    protected boolean isHeldExclusively() {
        return super.isHeldExclusively();
    }
}
