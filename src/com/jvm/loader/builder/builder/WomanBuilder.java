package com.jvm.loader.builder.builder;

import com.jvm.loader.builder.product.Person;
import com.jvm.loader.builder.product.Woman;

/**
 * 具体建造者
 */
public class WomanBuilder implements PersonBuilder {
    private Person person;

    public WomanBuilder() {
        this.person = new Woman();
    }

    @Override
    public void buildHead() {
        person.setHead("Pretty Head");

    }

    @Override
    public void buildBody() {
        person.setBody("soft body");

    }

    @Override
    public void buildFoot() {
        person.setFoot("long white foot");

    }

    @Override
    public Person createPerson() {
        return person;
    }

}
