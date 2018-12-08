package com.jvm.test;

public class StackAllocTest {

    private static class User{
        public int age;
        public String name;

    }

    public static void creatUser(){
        User u = new User();
        u.age=11;
        u.name="wy";
    }



    /**
     *    //没有设置JVM参数的情况下 所花时间：5ms
     *     //JVM参数:-server  -Xms10m -Xmx10m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations -XX:-UseTLAB  所花时间5ms
     *     //JVM参数:-server  -Xms10m -Xmx10m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-EliminateAllocations -XX:-UseTLAB  所花时间2245ms
     *
     *     参数解释：
     *     -server：JVM运行的模式；可选值client/mix/server 只有在server模式下才能进行逃逸分析
     *     -Xms10m -Xmx10m：堆大小
     *     -XX:+和-XX:-：表示开启/不开启某种功能
     *     -XX:+DoEscapeAnalysis 开启逃逸分析功能（默认是开启的）
     *     -XX:+PrintGC 开启GC日志打印
     *     -XX:+EliminateAllocations 开启标量替换  标量替换：例如user中的age和name值是否可以作为方法creatUser中的两个变量；当这个两个变量值变化时，只要变化creatUser中的这两个变量的值。
     *     -XX:-UseTLAB： 关闭线程本地分配缓存（因为此处给堆分配的空间就只有10m，作为例子不应该分配缓存）    ThreadLocalAllocBuffer 事先在堆里面为每个线程分配一定的内存；
     *     如果没有这个机制《线程本地分配缓存》的话；在多线程情况下分别申请内存空间，JVM必然要加锁为线程开辟空间；效率直线下降。
     *
     *     栈上分配的条件就是开启逃逸分析功能和开启标量替换；关掉任意一个都不会启用栈上分配
     * @param args
     */
    public static void main(String[] args) {
        long start =System.currentTimeMillis();
//        System.out.println(start);

        for (int i = 0 ;i<100000000;i++){
            creatUser();
        }
        long end =System.currentTimeMillis();
        System.out.println(end-start);

    }
}
