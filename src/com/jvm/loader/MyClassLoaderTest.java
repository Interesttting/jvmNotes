package com.jvm.loader;

public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader classLoader =new MyClassLoader();
        classLoader.setBasePath("/Users/mac/PROJECT/WORKSPACE_GITHUB/jvmNotes/src/");
        Class<?> clazz=classLoader.findClass("com.jvm.test.code.TestApp");
        System.out.println();
    }
}
