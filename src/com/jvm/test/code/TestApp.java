package com.jvm.test.code;

public class TestApp {
    private volatile int num = 0;


    public synchronized void abc() {
        num++;
    }

    public void def() {
            synchronized (this) {
            num++;
        }
    }


    public static void main(String[] args) {

    }

}
