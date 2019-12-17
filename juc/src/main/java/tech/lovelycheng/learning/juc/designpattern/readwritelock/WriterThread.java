package tech.lovelycheng.learning.juc.designpattern.readwritelock;

import java.util.Random;

/**
 * @author chengtong
 * @date 2019/12/16 17:47
 */
public class WriterThread extends Thread{


    private SharedResource sharedResource;
    private Random random = new Random(10000);
    private static int num = 0;
    WriterThread(SharedResource sharedResource){
        this.sharedResource = sharedResource;
        this.setName("writer"+num);
        num++;
    }



    @Override
    public void run() {
        for(int i=0;i<1000000;i++){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedResource.write(String.valueOf(random.nextBoolean()));
        }
    }
}
