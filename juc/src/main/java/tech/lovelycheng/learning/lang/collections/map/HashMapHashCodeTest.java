package tech.lovelycheng.learning.lang.collections.map;

/**
 * @author chengtong
 * @date 2019/12/29 11:24
 */
public class HashMapHashCodeTest {

    public static void main(String[] args) {
        String code = "blog.lovelycheng.tech";

        System.err.println(Integer.toBinaryString(code.hashCode()));
        System.err.println(Integer.toBinaryString(code.hashCode() >> 16));
        System.err.println(Integer.toBinaryString((code.hashCode() >> 16) ^ code.hashCode() ));

        System.err.println(Integer.toBinaryString(16-1));
        System.err.println(((code.hashCode() >> 16 ^ code.hashCode() ) & (16-1)));
    }





}
