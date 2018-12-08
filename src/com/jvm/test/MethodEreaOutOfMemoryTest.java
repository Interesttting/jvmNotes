package com.jvm.test;


public class MethodEreaOutOfMemoryTest {

    /**
     * JDK8
     *
     * JVM参数：-XX:MaxMetaspaceSize=3m
     *
     * 日志：
     * Error occurred during initialization of VM
     * MaxMetaspaceSize is too small.
     *
     */
    public static void main(String[] args) {

    }
}
