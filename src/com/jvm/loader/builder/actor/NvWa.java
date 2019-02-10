package com.jvm.loader.builder.actor;

import com.jvm.loader.builder.builder.PersonBuilder;
import com.jvm.loader.builder.product.Person;

/**
 * 类说明：导演者
 */
public class NvWa {

    public Person buildPerson(PersonBuilder pb) {
        pb.buildHead();
        pb.buildBody();
        pb.buildFoot();
        return pb.createPerson();
    }

}
