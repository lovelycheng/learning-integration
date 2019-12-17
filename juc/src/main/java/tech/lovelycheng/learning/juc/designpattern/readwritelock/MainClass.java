package tech.lovelycheng.learning.juc.designpattern.readwritelock;

/**
 * @author chengtong
 * @date 2019/12/16 17:57
 */
public class MainClass {

    public static void main(String[] args) {
        SharedResource resource = new SharedResourceWithJUC();

        new ReaderThread(resource).start();
        new ReaderThread(resource).start();
        new ReaderThread(resource).start();
        new ReaderThread(resource).start();

        new WriterThread(resource).start();

    }

}
