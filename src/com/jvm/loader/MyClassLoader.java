package com.jvm.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {

    private String basePath;

    private final static String FILE_FIX=".class";

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes =  readFile(name);

        return this.defineClass(name,bytes,0,bytes.length);
    }

    private byte[] readFile(String name) {
        String tempName = name.replaceAll("\\.","/");
        File file =new File(basePath+tempName+FILE_FIX);
        byte[] bs = new byte[0];
        try {
            FileInputStream ios =new FileInputStream(file);
            int available = ios.available();
            bs =new byte[available];
            ios.read(bs);
            ios.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return bs;
    }
}
