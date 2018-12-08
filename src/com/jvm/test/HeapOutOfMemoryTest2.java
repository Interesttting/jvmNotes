package com.jvm.test;

import java.util.ArrayList;
import java.util.List;

public class HeapOutOfMemoryTest2 {
    /**
     * JVM参数：-Xms5m -Xmx5m -XX:+PrintGC 堆大小设置为5M 并打印垃圾回收日志
     *
     *
     * 日志内容：
     * [GC 1137K->480K(7680K), 0.0012790 secs]
     * [GC 480K->432K(7680K), 0.0009100 secs]
     * [Full GC 432K->306K(6144K), 0.0098730 secs]
     * [GC 306K->306K(7680K), 0.0008780 secs]
     * [Full GCException in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	at java.util.ArrayList.<init>(ArrayList.java:144)
     * 	at com.jvm.test.HeapOutOfMemoryTest2.main(HeapOutOfMemoryTest2.java:15)
     *  306K->292K(7168K), 0.0052390 secs]
     */
    public static void main(String[] args) {
        List<Object> list =new ArrayList<>(1000000000);
    }
}
