package tech.lovelycheng.learning.juc.designpattern.future;

/**
 * @author chengtong
 * @date 2020/1/6 15:25
 */
public class Host {

    Data request(){

        FutureData futureData = new FutureData();

        new Thread(() -> {
            RealData realData = new RealData();
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            realData.setContent("dasdasdasd");
            futureData.setRealData(realData);
        }).start();

        return futureData;

    }



}
