package tech.lovelycheng.learning.javalang.hAndE;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author chengtong
 * @date 2020/2/23 15:42
 *
 * 默认的哈希算法实现的碰撞概率 -XX:hashCode = 1\2\3\4\5
 *
 */
public class Collision {

    static long times =0;
    static long collisions =0;

    public static void main(String[] args) {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        for(String s:arguments){
            System.out.println(s);
        }
        for(int i=0;i<10;i++){
            collisions += cacl();
        }

        System.out.println("average collisions:"+collisions/10);
        System.out.println("average usedTime:"+times/10);

    }

    private static int cacl() {
        long now = System.currentTimeMillis();

        HashMap<Integer,Object> h = new HashMap<>();

        ArrayList<Object> arrayList = new ArrayList<>();

        for(int i=0;i<20000000;i++){
            Object o = new Object();
            if(h.containsKey(o.hashCode())){
//                System.err.println("hashcode collision:"+ o.hashCode());
                arrayList.add(o);
                continue;
            }
            h.put(o.hashCode(),o);
        }
        long currentTimeMillis = System.currentTimeMillis();
        System.err.println("hashcode collision："+arrayList.size());
        System.err.println(" used time "+(currentTimeMillis - now));

        times += (currentTimeMillis-now);

        return arrayList.size();

    }


}
