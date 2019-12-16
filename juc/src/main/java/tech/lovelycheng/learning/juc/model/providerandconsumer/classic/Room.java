package tech.lovelycheng.learning.juc.model.providerandconsumer.classic;

import java.util.ArrayList;

/**
 * @author chengtong
 * @date 2019/12/13 15:04
 */
class Room {

    private final ArrayList<String> names = new ArrayList<>();

    synchronized void put(String name) {

        while (!names.isEmpty()) {
            try {
                System.err.println(Thread.currentThread().getName() + "wait on names is not empty");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("put in name :" + name);
        names.add(name);
        notifyAll();
    }

    synchronized String take() {

        while (names.isEmpty()) {
            try {
                System.err.println(Thread.currentThread().getName() + "wait on names is empty");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("take last name ");
        String name = names.remove(names.size() - 1);
        notifyAll();
        return name;
    }


}
