package com.jvm.loader.builder.builder;

import com.jvm.loader.builder.product.Person;

/**
 * 抽象建造者
 */
public interface PersonBuilder {

    //建造部件
    public abstract void buildHead();
    public abstract void buildBody();
    public abstract void buildFoot();

    public abstract Person createPerson();

}
