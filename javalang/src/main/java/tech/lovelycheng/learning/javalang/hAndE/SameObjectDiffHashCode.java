package tech.lovelycheng.learning.javalang.hAndE;

import java.util.HashMap;
import java.util.Random;

/**
 * @author chengtong
 * @date 2020/2/23 15:32
 * 这个测试违反常规，"if(a.equals(b)) a.hashcode() == b.hashcode() "
 * 所以这个类在哈希容器中无法正常使用。
 *
 *
 */
public class SameObjectDiffHashCode {

    private Random random = new Random();
//    private Integer hc ;
    @Override
    public int hashCode() {
//        if(hc != null ){
//            return hc;
//        }
        return random.nextInt();

    }


    public static void main(String[] args) {
        SameObjectDiffHashCode c = new SameObjectDiffHashCode();
        System.err.println(c.hashCode());
        System.err.println(c.hashCode());
        SameObjectDiffHashCode d = getO(c);
        System.err.println(c == d);
        System.err.println(c.equals(d));

        HashMap<SameObjectDiffHashCode,String> hashCodeStringHashMap = new HashMap<>();

        hashCodeStringHashMap.put(c,"sdasd");

        System.out.println(hashCodeStringHashMap.get(c));

    }

    private static SameObjectDiffHashCode getO(SameObjectDiffHashCode c) {
        return c;
    }


}
