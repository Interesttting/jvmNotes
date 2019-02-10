package com.jvm.builder.client;

import com.jvm.builder.actor.NvWa;
import com.jvm.builder.builder.ManBuilder;
import com.jvm.builder.builder.WomanBuilder;

/**
 * 类说明：客户端
 */
public class Mingyun {

    public static void main(String[] args) {
        System.out.println("create NvWa");
        NvWa nvwa = new NvWa();
        nvwa.buildPerson(new ManBuilder());
        nvwa.buildPerson(new WomanBuilder());
    }
}
