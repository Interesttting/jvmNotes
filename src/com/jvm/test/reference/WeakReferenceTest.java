package com.jvm.test.reference;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

    /**
     * JVM参数：无
     *
     * 日志：
     * GC after
     * -----------------soft:null
     *
     *
     * 弱引用拓展：WeakHashMap 存储弱引用对象
     *
     */
    public static void main(String[] args) {
        Object o =new Object();
        WeakReference<Object> soft =new WeakReference<>(o);
        o=null;
        //通过以上代码 o对象从强引用变为弱引用关联
        //手动的gc可以回收弱引用对象
        System.gc();//开发中不能使用
        System.out.println("GC after");
        System.out.println("-----------------soft:"+soft.get());//-----------------soft:null

    }
}
