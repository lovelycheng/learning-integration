package tech.lovelycheng.learning.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chengtong
 * @date 2020/12/28 08:11
 */
public class MyLock extends AbstractQueuedSynchronizer {

    public void lock(){
        this.acquire(1);
    }


    @Override
    protected boolean tryAcquire(int arg) {
        int c = getState();
        if(c == 0){
            if(compareAndSetState(0,1)){
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
        }else {
            if(Thread.currentThread().equals(this.getExclusiveOwnerThread())){
                return true;
            }
        }
        return false;
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

}
