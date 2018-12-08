package com.jvm.test;

import java.util.ArrayList;
import java.util.List;

public class HeapOutOfMemoryTest {
    /**
     * JVM参数：-Xms5m -Xmx5m -XX:+PrintGC 堆大小设置为5M 并打印垃圾回收日志
     *
     *
     * 日志内容：
     * [Full GC 7076K->7076K(7680K), 0.0355410 secs]
     * [Full GC 7077K->7077K(7680K), 0.0329200 secs]
     * [Full GC 7078K->7078K(7680K), 0.0336120 secs]
     * [Full GC 7079K->7079K(7680K), 0.0323550 secs]
     * [Full GCException in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
     *  7094K->323K(7168K), 0.0069070 secs]
     * 	at com.jvm.test.HeapOutOfMemoryTest.main(HeapOutOfMemoryTest.java:27)
     */
    public static void main(String[] args) {
        List<Object> list =new ArrayList<>();
        int i =0;
        while (true){
            i++;
            if (i%10000==0) System.out.println("i="+i);
            list.add(new Object());
        }
    }
}
