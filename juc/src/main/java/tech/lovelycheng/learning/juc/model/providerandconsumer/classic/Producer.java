package tech.lovelycheng.learning.juc.model.providerandconsumer.classic;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 2019/12/13 15:03
 */
public class Producer implements Runnable, Serializable {

    private final Room room;

    public Producer(Room room) {
        this.room = room;
    }


    @Override
    public void run() {

        String[] names = {"2", "123", "432345", "23453452"};

        for (String name : names) {
            room.put(name);
        }

        System.err.println("exit");
    }
}
