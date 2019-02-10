package com.jvm.test.code;

public class Exercise {

    static void print(Exercise e1 ,Exercise e2){
        System.out.println(e1==(e1=e2));//false
        System.out.println((e1=e2).toString());
        System.out.println(e1.toString());

        System.out.println();
    }

    public static void main(String[] args) {
        Exercise e1 =new Exercise();
        Exercise e2 =new Exercise();
        print(e1,e2);
    }
}
