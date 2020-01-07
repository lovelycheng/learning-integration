package tech.lovelycheng.learning.juc.designpattern.future;

/**
 * @author chengtong
 * @date 2020/1/6 15:22
 */
public class FutureData implements Data{

    private Data realData;

    private boolean isReady;

    public synchronized void setRealData(Data realData) {
        if(isReady){
            return;
        }
        isReady = true;
        this.realData = realData;
        notify();
    }

    @Override
    public synchronized String getContent() {

        if(!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return realData.getContent();
    }
}
