package com.founder.xmemcached.bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author Mr.jt
 * @create 2022-08-02 14:16
 */
public class Student implements Serializable {
    @ExcelProperty(value = "姓名",index = 0)
    private String name;
    @ExcelProperty(value = "年龄",index = 1)
    private Integer age;
    @ExcelProperty(value = "地址",index = 2)
    private String address;
    @ExcelProperty(value = "性别",index = 3)
    private String gender;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
    @ExcelIgnore
    private Integer height;

    public Student() {
    }

    public Student(String name, Integer age, String address, String gender) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                '}';
    }


    public static void main(String[] args) {

    }
}
