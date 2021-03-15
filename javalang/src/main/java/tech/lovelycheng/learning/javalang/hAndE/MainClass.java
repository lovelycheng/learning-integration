package tech.lovelycheng.learning.javalang.hAndE;

import sun.jvm.hotspot.opto.HaltNode;

import java.util.*;

/**
 * @author chengtong
 * @date 2020/2/23 13:20
 */
public class MainClass {

    public static void main(String[] args) {

//        IdentityCard identityCard = new IdentityCard(2,330,122,19871024,2910);
//        IdentityCard identityCard2 = new IdentityCard(2,330,122,19871024,2910);

//        identityCard == identityCard2;
//        HashMap<IdentityCard,String> stringHashMap = new HashMap<>();
//        stringHashMap.put(identityCard,"bb");
//        String s = stringHashMap.get(identityCard2);
//        System.err.println(s);
//        System.err.println(identityCard == identityCard2);

//        System.err.println(identityCard.equals(identityCard2));


//        List<Integer> list = new ArrayList<>();
//
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//        list.add(5);
//
//        for (Integer s: list){
//            System.out.println(s);
//            list.remove(2);
//        }

        HashMap<String,Object> stringObjectHashMap = new HashMap<>();

        stringObjectHashMap.put("1",new Object());
        stringObjectHashMap.put("2",new Object());

        Iterator<Map.Entry<String,Object>> iterator = stringObjectHashMap.entrySet().iterator();
        System.out.println(stringObjectHashMap.size());
        try{
            while (iterator.hasNext()){
                Map.Entry entry  = iterator.next();

                entry.getKey();
                entry.getValue();
                stringObjectHashMap.remove("1");
            }
        }catch (Exception e){

        }
        System.out.println(stringObjectHashMap.size());

    }

}
