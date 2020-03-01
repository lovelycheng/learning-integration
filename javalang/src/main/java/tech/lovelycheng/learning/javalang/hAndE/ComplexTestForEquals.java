package tech.lovelycheng.learning.javalang.hAndE;

import java.util.Objects;

/**
 * @author chengtong
 * @date 2020/2/26 03:05
 */
public class ComplexTestForEquals {

    public static void main(String[] args) {

        Integer a = 127;

        Integer b = 127;

        System.out.println(a == b);

        Cope cope = new Cope();

        ComplexTestForEquals complexTestForEquals = new ComplexTestForEquals();

        System.err.println(cope.equals(complexTestForEquals));
//        System.err.println(cope == (complexTestForEquals));


    }

}
