package tech.lovelycheng.learning.lang.collections.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author chengtong
 * @date 2019/12/30 09:45
 */
public class ConcurrentHashMapTest {

    private static final int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 16;

    public static void main(String[] args) {

//        System.err.println(Integer.toBinaryString(0x7fffffff ));
//        System.err.println(Integer.toBinaryString(Integer.MAX_VALUE));
//
//
//        System.err.println(Integer.toBinaryString(resizeStamp(12)));
//
//        System.err.println((resizeStamp(12)));
//        System.err.println(Integer.numberOfLeadingZeros(16));
//
//         在二进制表示中 开头有几位0
//        System.err.println(Integer.numberOfLeadingZeros(Integer.MAX_VALUE)+" expect 1");
//        System.err.println(Integer.numberOfLeadingZeros(0)+" expect 32");
//
//        System.err.println(Integer.toBinaryString(resizeStamp(0)));
//        System.err.println(Integer.toBinaryString(resizeStamp(Integer.MAX_VALUE)));

//        System.err.println(Integer.toBinaryString(resizeStampNoOr(0)));
//        System.err.println(Integer.toBinaryString(resizeStampNoOr(Integer.MAX_VALUE)));

        // 是一个 1-32之间的数 二进制的表示 是 100000
//        System.err.println(Integer.toBinaryString((1 << (RESIZE_STAMP_BITS - 1))));

        int sc = -2; // 现在有一根线程在做resize

//        System.err.println(Integer.toBinaryString((sc >>> RESIZE_STAMP_SHIFT)));
//        System.err.println((sc >>> RESIZE_STAMP_SHIFT));

//        System.err.println(Integer.toBinaryString(  resizeStamp(32)) + "   resizeStamp 32");

//        int stamp = resizeStamp(32);
//        System.err.println((stamp << 16) + 2 + "  (stamp << 16) + 2");
//        System.err.println(Integer.toBinaryString((stamp << 16) + 2));

//        System.err.println(Integer.toBinaryString(  resizeStamp(16)));
//        System.err.println(Integer.toBinaryString(  resizeStamp(8)));
//        System.err.println(Integer.toBinaryString(  resizeStamp(4)));
//        System.err.println(Integer.toBinaryString(  resizeStamp(2)));
//        System.err.println(resizeStamp(32));
//        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

//        concurrentHashMap.reduce(1, (o, o2) -> null, (o, o2) -> null);

//        System.err.println(tableSizeFor(49));
//        System.err.println(32 + (32 >>> 1) + 1);

        ConcurrentHashMap<NullHashObject, Object> map = new ConcurrentHashMap<>();
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);
        map.put(new NullHashObject(),11);



    }

    private static class NullHashObject{
        @Override
        public int hashCode() {
            return 0;
        }
    }

    // 其实就是 16位不动 低6位变化
    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
    static final int resizeStampNoOr(int n) {
        return Integer.numberOfLeadingZeros(n);
    }

    private static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * Returns a power of two table size for the given desired capacity.
     * See Hackers Delight, sec 3.2
     */
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
