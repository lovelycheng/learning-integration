package tech.lovelycheng.learning.juc.lock.spinlock;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @author chengtong
 * @date 2019/12/17 15:06
 */
public class SpinLockChannel {

    private ArrayList<String> arrayList = new ArrayList<>();

    private final SpinLock spinLockWithTicket ;

    public SpinLockChannel(SpinLock spinLockWithTicket) {
        this.spinLockWithTicket = spinLockWithTicket;
    }


    public void write(String s){

        spinLockWithTicket.lock();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrayList.add(s);

        System.err.println(JSON.toJSONString(arrayList));

        spinLockWithTicket.unlock();

    }





}
