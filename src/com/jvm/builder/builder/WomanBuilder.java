package com.jvm.builder.builder;

import com.jvm.builder.product.Person;
import com.jvm.builder.product.Woman;

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
