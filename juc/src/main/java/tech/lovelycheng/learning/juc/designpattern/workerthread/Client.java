package tech.lovelycheng.learning.juc.designpattern.workerthread;

/**
 * @author chengtong
 * @date 2020/1/6 13:09
 */
public class Client {

    private Channel channel ;

    Client(Channel channel){
        this.channel = channel;
    }

    public void start(){
        for(int i =0;i<1000;i++){
            this.channel.putRequest(() -> System.err.println(Thread.currentThread().getName()+" execution "));
        }
    }







}
