package tech.lovelycheng.learning.javalang.hAndE;

/**
 * @author chengtong
 * @date 2020/2/23 11:48
 */
public class Dameda {


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    public static void main(String[] args) {

        Object op = new Object();
        Object op1 = new Object();
        Object op2 = new Object();
        Object op5 = new Object();
        Object op3 = new Object();
        Object op4 = new Object();

        System.err.println(op.hashCode());
        System.err.println(op1.hashCode());
        System.err.println(op2.hashCode());
        System.err.println(op3.hashCode());
        System.err.println(op4.hashCode());
        System.err.println(op5.hashCode());

        System.gc();

        System.err.println("         ");
        System.err.println(op.hashCode());
        System.err.println(op1.hashCode());
        System.err.println(op2.hashCode());
        System.err.println(op3.hashCode());
        System.err.println(op4.hashCode());
        System.err.println(op5.hashCode());


    }

}
