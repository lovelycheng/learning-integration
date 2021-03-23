package tech.lovelycheng.learning.javalang.threadlocal;

import java.lang.reflect.Field;


/**
 * @author chengtong
 * @date 2020/2/17 12:39
 */
public class ThreadLocalDemo {

    static byte[] bytes = new byte[1024 * 1024 * 10];

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000000; i++) {
            getThreadLocal();
        }

//        Field map = Thread.class.getDeclaredField("threadLocals");
//
//        map.setAccessible(true);
//        Object o  = map.get(Thread.currentThread());// thread.threadlocals
//
//        System.out.println(o);

    }

    private static void getThreadLocal() {

        MyThread e = new MyThread(() -> {
            new ThreadLocal<>().set(bytes);
//            Field map = null;
//            try {
//                map = Thread.class.getDeclaredField("threadLocals");
//                map.setAccessible(true);
//                Object o  = map.get(Thread.currentThread());// thread.threadlocals
//
//                Class[] classes = ThreadLocal.class.getDeclaredClasses();
//
//                for(Class clazz:classes){
//                    if(clazz.getName().equals("java.lang.ThreadLocal$ThreadLocalMap")){
//                        Field f = clazz.getDeclaredField("table");
//                        f.setAccessible(true);
//                        Object entries = f.get(o);
//                        System.out.println(entries);
//                        if(entries instanceof Reference[]){
//
//                            for(Reference reference:(Reference[])entries){
//                                if(reference != null) {
//                                    System.err.println("key:" + reference.get());
//                                    System.err.println("value:" + getDeclaredValue(reference, "value"));
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//            } catch (NoSuchFieldException ex) {
//                ex.printStackTrace();
//            } catch (IllegalAccessException ex) {
//                ex.printStackTrace();
//            }
        });

        e.start();
    }

    public static Object getDeclaredValue(Object source, String fieldName) {

        try {
            Field f = source.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(source);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }


}
