package com.jvm.test.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SoftReferenceTest {

    /**
     * JVM参数：-Xms5m -Xmx5m -XX:+PrintGC
     *
     * 日志
     * [GC (Allocation Failure)  1020K->512K(5632K), 0.0015093 secs]
     * [GC (System.gc())  1076K->520K(5632K), 0.0014938 secs]
     * [Full GC (System.gc())  520K->382K(5632K), 0.0046658 secs]
     * soft:java.lang.Object@610455d6   list.add第1次
     * soft:java.lang.Object@610455d6
     * soft:java.lang.Object@610455d6
     * soft:java.lang.Object@610455d6
     * soft:java.lang.Object@610455d6    list.add第6次 第7次时发现内存不足；调用gc，此时回收了软引用对象
     * [GC (Allocation Failure)  3521K->3646K(5632K), 0.0006899 secs]
     * [GC (Allocation Failure)  3646K->3582K(5632K), 0.0010919 secs]
     * [Full GC (Allocation Failure)  3582K->3454K(5632K), 0.0048799 secs]
     * [GC (Allocation Failure)  3454K->3454K(5632K), 0.0010502 secs]
     * [Full GC (Allocation Failure) java.lang.OutOfMemoryError: Java heap space
     * 	at com.jvm.test.reference.SoftReferenceTest.main(SoftReferenceTest.java:20)
     *  3454K->3437K(5632K), 0.0047282 secs]
     * soft:null
     */
    public static void main(String[] args) {
        Object o =new Object();
        SoftReference<Object> soft =new SoftReference<>(o);
        o=null;
        //通过以上代码 o对象从强引用变为软引用关联
        System.gc();//开发中不能使用
        //gc调用后并不会被回收
        System.out.println("-----------------soft:"+soft.get());//-----------------soft:java.lang.Object@610455d6
        List<byte[]> list =new ArrayList<>();

        try {
            for (int i=0;i<100;i++){
                System.out.println("-----------------soft:"+soft.get());//-----------------soft:java.lang.Object@610455d6
                list.add(new byte[1024*1024*1]);
            }
        }catch (Throwable e){
            e.printStackTrace();
            //OOM发生后
            System.out.println("-----------------soft:"+soft.get());//-----------------soft:null
        }
    }
}
