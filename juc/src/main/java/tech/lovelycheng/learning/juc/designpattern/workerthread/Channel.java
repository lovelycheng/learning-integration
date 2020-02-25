package tech.lovelycheng.learning.juc.designpattern.workerthread;

/**
 * @author chengtong
 * @date 2020/1/6 13:08
 */
public class Channel {

    private volatile Request head;
    private volatile Request tail;

    private WorkerThread[] pool = new WorkerThread[8];

    Channel(){
        for(int i=0;i<pool.length;i++){
            pool[i] = new WorkerThread(this,"thead"+i);
            pool[i].start();
        }
    }

    public synchronized void  putRequest(Runnable runnable){
        Request request= new Request(runnable);
        if(head == null && tail == null){
            head = request;
            tail = request;
        }else {
            tail.next = request;
            tail = request;
        }
        notifyAll();
    }

    public synchronized Request takeRequest(){
        Request h = head;
        while ( h == null){
            try {
                wait();
                h = head;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Request n = h.next;
        if(n != null){
            head = n;
            h.next = null;
        }else {
            head = null;
            tail = null;
        }

        return h;
    }

    private static class Request implements Runnable{

        Request next;

        Runnable runnable;

        Request(Runnable runnable){
            this.runnable = runnable;
        }

        public void setNext(Request next) {
            this.next = next;
        }


        @Override
        public void run() {
            this.runnable.run();
        }
    }

    private static class WorkerThread extends Thread{

        Channel channel;
        String name;

        WorkerThread(Channel channel,String name){
            this.channel = channel;
            this.name = name;
        }

        @Override
        public void run() {
            Request r ;
            while ((r = channel.takeRequest()) != null){
                r.run();
            }
        }
    }

}
