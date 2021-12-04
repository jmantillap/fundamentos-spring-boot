package com.fundamentosplatzi.springboot.fundamentos.bean;

public class MyBeanWithPropertiesImplement implements  MyBeanWithProperties{

    private String name;
    private String apellido;

    public MyBeanWithPropertiesImplement(String name,String apellido) {
        this.apellido=apellido;
        this.name=name;
    }

    @Override
    public String function() {
        return name + "- " + apellido;
    }
}
