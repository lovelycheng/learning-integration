package com.treefinance.clearing.accounting.accountdata.handle.global;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author chengtong
 * @date 2019/12/26 11:24
 */
public class HashMapTest {

    public static void main(String[] args) {

        System.err.println(Integer.toBinaryString(1 << 30));


        int c = tableSizeFor(1);
        System.err.println(c);
//        c = tableSizeFor(2);
//        System.err.println(c);
//        c = tableSizeFor(3);
//        System.err.println(c);
//        c = tableSizeFor(4);
//        System.err.println(c);
//        c = tableSizeFor(5);
//        System.err.println(c);
//        c = tableSizeFor(6);
//        System.err.println(c);
//        c = tableSizeFor(7);
//        System.err.println(c);
//        c = tableSizeFor(8);
//        System.err.println(c);
//        c = tableSizeFor(9);
//        System.err.println(c);
//        c = tableSizeFor(10);
//        System.err.println(c);
        c = tableSizeFor(17);
        System.err.println(c);

        HashMap hashMap = new HashMap(64);
        hashMap.put(new NullHashObject(),1);
        hashMap.put(new NullHashObject(),2);
        hashMap.put(new NullHashObject(),3);
        hashMap.put(new NullHashObject(),4);
        hashMap.put(new NullHashObject(),5);
        hashMap.put(new NullHashObject(),6);
        hashMap.put(new NullHashObject(),7);
        hashMap.put(new NullHashObject(),8);
        hashMap.put(new NullHashObject(),0);

        HashMap.Entry[] nodes = new java.util.Map.Entry[0];
        try {
            nodes = (HashMap.Entry[]) getField("table",hashMap,HashMap.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.err.println(nodes.length+" expect 1");
        System.err.println(nodes[0]+" node 0");
        System.err.println(nodes[1]+" node 1");
        System.err.println(nodes[2]+" node 2");
        System.err.println(nodes[3]+" node 3");
        System.err.println(nodes[4]+" node 3");
        System.err.println(nodes[5]+" node 3");
        System.err.println(nodes[6]+" node 3");
        System.err.println(nodes[7]+" node 3");
        System.err.println(nodes[8]+" node 3");
        System.err.println(hashMap.keySet().size()+" expect 3");
        System.err.println(hashMap.size()+" expect 3");


    }

    private static class NullHashObject{
        @Override
        public int hashCode() {
            return 0;
        }
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        System.err.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.err.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.err.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.err.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.err.println(Integer.toBinaryString(n));
        return (n < 0) ? 1 : (n >= 1 << 30) ? 1 << 30 : n + 1;
    }

    public static Object getField(String name, Object o, Class clazz) throws IllegalAccessException {
        Field[] fs
                = clazz.getDeclaredFields();
        Field f = null;
        for (Field field : fs) {
            field.setAccessible(true);
            if (field.getName().equals(name)) {
                f = field;
            }
        }

        assert f != null;
        return f.get(o);
    }


}
