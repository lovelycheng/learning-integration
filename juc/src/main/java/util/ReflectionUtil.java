package util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chengtong
 * @date 2019/12/10 10:33
 */
public class ReflectionUtil {

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

    public static int workerCountOf(int c)  { return c & ((1 << 29) - 1); };

}
