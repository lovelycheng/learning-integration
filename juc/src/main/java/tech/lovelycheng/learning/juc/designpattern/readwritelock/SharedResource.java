package tech.lovelycheng.learning.juc.designpattern.readwritelock;

/**
 * @author chengtong
 * @date 2019/12/16 17:39
 */
public class SharedResource {

    ReadWriteWithCountLock lock = new ReadWriteWithCountLock();

    String content = "*";

    public void readLock(){
        lock.lockReadLock();
    }

    public void unlockReadLock(){
        lock.unlockReadLock();
    }

    public void writeLock(){
        lock.lockWriteLock();
    }

    public void unlockWriteLock(){
        lock.unlockWriteLock();
    }


    public String read(){
        readLock();
        try {
//            Thread.sleep(10000);
            System.err.println(content);
            return content;
//        } catch (InterruptedException e) {
//            return content;
        } finally {
            unlockReadLock();
        }
    }

    public void write(String newContent){
        writeLock();
        StringBuffer stringBuffer = new StringBuffer(content);
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stringBuffer.append(newContent);
        this.content = stringBuffer.toString();
        unlockWriteLock();
    }

}
