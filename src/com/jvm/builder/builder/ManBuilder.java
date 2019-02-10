package com.jvm.builder.builder;

import com.jvm.builder.product.Man;
import com.jvm.builder.product.Person;

/**
 * 具体建造者
 */
public class ManBuilder implements PersonBuilder {

    private Person person;

    public ManBuilder() {
        this.person = new Man();
    }

    @Override
    public void buildHead() {
        person.setHead("Brave Head");

    }

    @Override
    public void buildBody() {
        person.setBody("Strong body");

    }

    @Override
    public void buildFoot() {
        person.setFoot("powful foot");

    }

    @Override
    public Person createPerson() {
        return person;
    }



}