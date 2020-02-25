package tech.lovelycheng.learning.javalang.hAndE;

import sun.jvm.hotspot.opto.HaltNode;

import java.util.HashMap;

/**
 * @author chengtong
 * @date 2020/2/23 13:20
 */
public class MainClass {

    public static void main(String[] args) {

        IdentityCard identityCard = new IdentityCard(2,330,122,19871024,2910);
        IdentityCard identityCard2 = new IdentityCard(2,330,122,19871024,2910);

//        identityCard == identityCard2;
//        HashMap<IdentityCard,String> stringHashMap = new HashMap<>();
//        stringHashMap.put(identityCard,"bb");
//        String s = stringHashMap.get(identityCard2);
//        System.err.println(s);
//        System.err.println(identityCard == identityCard2);

        System.err.println(identityCard.equals(identityCard2));

    }




}
