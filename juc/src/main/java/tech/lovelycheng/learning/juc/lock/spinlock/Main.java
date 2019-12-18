package tech.lovelycheng.learning.juc.lock.spinlock;


/**
 * @author chengtong
 * @date 2019/12/17 15:12
 */
public class Main {

    public static void main(String[] args) {

//        fairSpinLockWithTicket();
//        arrayBasedFairSpinLock();
//        linkedFairSpinLock();
        clhSpinLock();

    }

    private static void fairSpinLockWithTicket() {
        SpinLockChannel spinLockChannel = new SpinLockChannel(new SpinLockWithTicket());

        new WriterThread("writer1",spinLockChannel).start();
        new WriterThread("writer2",spinLockChannel).start();
        new WriterThread("writer3",spinLockChannel).start();
        new WriterThread("writer4",spinLockChannel).start();
    }

    private static void arrayBasedFairSpinLock() {
        SpinLockChannel spinLockChannel = new SpinLockChannel(new ArrayBasedFairSpinLock());

        new WriterThread("writer1",spinLockChannel).start();
        new WriterThread("writer2",spinLockChannel).start();
        new WriterThread("writer3",spinLockChannel).start();
        new WriterThread("writer4",spinLockChannel).start();
    }
    private static void linkedFairSpinLock() {
        SpinLockChannel spinLockChannel = new SpinLockChannel(new MSCLockSpinLock());

        new WriterThread("writer1",spinLockChannel).start();
        new WriterThread("writer2",spinLockChannel).start();
        new WriterThread("writer3",spinLockChannel).start();
        new WriterThread("writer4",spinLockChannel).start();
        new WriterThread("writer5",spinLockChannel).start();
        new WriterThread("writer6",spinLockChannel).start();
        new WriterThread("writer7",spinLockChannel).start();
        new WriterThread("writer8",spinLockChannel).start();
        new WriterThread("writer9",spinLockChannel).start();
        new WriterThread("writer10",spinLockChannel).start();
        new WriterThread("writer11",spinLockChannel).start();
    }

    private static void clhSpinLock() {
        SpinLockChannel spinLockChannel = new SpinLockChannel(new CLHSpinLock());

        new WriterThread("writer1",spinLockChannel).start();
        new WriterThread("writer2",spinLockChannel).start();
        new WriterThread("writer3",spinLockChannel).start();
        new WriterThread("writer4",spinLockChannel).start();
        new WriterThread("writer5",spinLockChannel).start();
        new WriterThread("writer6",spinLockChannel).start();
        new WriterThread("writer7",spinLockChannel).start();
        new WriterThread("writer8",spinLockChannel).start();
        new WriterThread("writer9",spinLockChannel).start();
        new WriterThread("writer10",spinLockChannel).start();
        new WriterThread("writer11",spinLockChannel).start();
    }
}
