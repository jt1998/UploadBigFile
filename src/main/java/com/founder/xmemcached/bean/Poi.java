package com.founder.xmemcached.bean;

import java.io.Serializable;

/**
 * @author Mr.jt
 * @create 2022-08-03 9:47
 */
public class Poi implements Serializable {
    private final static long serialVersionUID = 11232L;

    private String name;
    private Integer age;
    /*学习标识符，1代表高中，2代表大学，3代表硕士，4代表博士*/
    private Integer degree;
    private String address;

    public Poi() {
    }

    public Poi(String name, Integer age, Integer degree, String address) {
        this.name = name;
        this.age = age;
        this.degree = degree;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Poi{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", degree=" + degree +
                ", address='" + address + '\'' +
                '}';
    }
}
