package tech.lovelycheng.learning.lang;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengtong
 * @date 2019/12/13 11:33
 */
public class MultiThreadObjectLeakPrematurely {

    public static void main(String[] args) throws InterruptedException {

        List<Node> list = new ArrayList<>();

        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ;i< 10;i++){
                    System.err.println(JSON.toJSONString(list));
                }
            }
        });

        one.start();
        Thread.sleep(61);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);
        new Node(list);





    }

    private static class Node{

        private int age = 0;

        Node(List<Node> list){
            list.add(this);
            this.age = 15;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "age=" + age +
                    '}';
        }
    }





}
