package com.jvm.test;

public class StackOutOfMemoryTest {
    static int stackLength = 0;
    /**
     *
     * JVM参数：-Xss256k 栈大小256k
     *
     * 日志：
     *  java.lang.StackOverflowError
     * 	at com.jvm.test.StackOutOfMemoryTest.recursion(StackOutOfMemoryTest.java:29)
     * 	at com.jvm.test.StackOutOfMemoryTest.recursion(StackOutOfMemoryTest.java:29)
     * stackLength:2447 栈深度  new StackOutOfMemoryTest().recursion();
     *
     * stackLength:1597  new StackOutOfMemoryTest().recursion("wy",18);
     *
     * 由此可见方法参数保存是占用着栈空间；参数越多占用空间越大
     *
     */
    public static void main(String[] args) {
        try {
//            new StackOutOfMemoryTest().recursion();
            new StackOutOfMemoryTest().recursion("wy",18);
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("stackLength:" + stackLength);


        }
    }

    /**
     * recursion 递归
     */
    public void recursion() {
        stackLength++;

        recursion();
    }
    public void recursion(String name,int age) {
        stackLength++;

        recursion(name,age);
    }
}
