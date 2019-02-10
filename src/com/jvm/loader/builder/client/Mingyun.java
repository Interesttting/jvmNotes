package com.jvm.loader.builder.client;

import com.jvm.loader.builder.actor.NvWa;
import com.jvm.loader.builder.builder.ManBuilder;
import com.jvm.loader.builder.builder.WomanBuilder;

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
