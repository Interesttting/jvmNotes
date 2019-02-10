package com.jvm.test.jdk.tool;

import java.util.Map;

public class JstackToolTest {

    public static void main(String[] args) {
        while(true)
        {
            byte[] b = null;
            for (int i = 0; i < 10; i++)
                b = new byte[1 * 1024 * 1024];

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //获取所有的线程信息对象
            Map<Thread, StackTraceElement[]> threadMap =  Thread.getAllStackTraces();
            for(Map.Entry<Thread, StackTraceElement[]> entry:
                    threadMap.entrySet()) {
                Thread t = entry.getKey();
                StackTraceElement[] ss = entry.getValue();
                //打印线程名和ID
                System.out.println(t.getName()+"-"+t.getId());
                //打印线程栈
                for(StackTraceElement s:ss) {
                    System.out.println(s);
                }

            }
            System.out.println("********************************************");

        }
    }
}
