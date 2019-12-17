package tech.lovelycheng.learning.juc.designpattern.readwritelock;

/**
 * @author chengtong
 * @date 2019/12/16 17:47
 */
public class ReaderThread extends Thread{

    private SharedResource sharedResource;

    private static int num = 0;

    ReaderThread(SharedResource sharedResource){
        this.sharedResource = sharedResource;
        this.setName("reader"+num);
        num++;
    }



    @Override
    public void run() {
        for(int i=0;i<1000000;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String content = sharedResource.read();
            System.err.println(content);
        }
    }
}
