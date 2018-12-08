package com.jvm.test;

import java.nio.ByteBuffer;

public class DirectMemoryOutOfMemoryTest {

    /**
     * 直接内存主要使用在nio dobbo、netty
     *
     * JVM参数：-XX:MaxDirectMemorySize=10m
     *
     * 日志：
     * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory  直接内存溢出
     */
    public static void main(String[] args) {
        ByteBuffer byteBuffer =ByteBuffer.allocateDirect(1024*1024*14);
    }

}
