package tech.lovelycheng.learning.juc.model.providerandconsumer.classic;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 2019/12/13 15:03
 */
public class Consumer implements Runnable, Serializable {

    private final Room room;

    public Consumer(Room room) {
        this.room = room;
    }

    @Override
    public void run() {

        for (String name = room.take();name != null;name = room.take()) {
            System.err.println("remove:" + name);
        }

    }
}
