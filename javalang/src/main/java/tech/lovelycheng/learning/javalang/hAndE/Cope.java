package tech.lovelycheng.learning.javalang.hAndE;

import java.util.Objects;

/**
 * @author chengtong
 * @date 2020/2/26 03:18
 */
public class Cope implements Cloneable{

    String name;
    int age;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cope)) return false;
        Cope cope = (Cope) o;
        return age == cope.age &&
                name.equals(cope.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
