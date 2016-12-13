package com.example.qq.entity;

import java.util.Arrays;

public class User {
    private String netName;
    private int age;
    private byte[] head_photo;
    private String sex;
    private String phoneNum;

    public User() {

    }

    public User(String netName, String age, byte[] head_photo, String sex, String phoneNum) {
        this.netName = netName;
        this.age = Integer.parseInt(age);
        this.head_photo = head_photo;
        this.sex = sex;
        this.phoneNum = phoneNum;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getHead_photo() {
        return head_photo;
    }

    public void setHead_photo(byte[] head_photo) {
        this.head_photo = head_photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "netName='" + netName + '\'' +
                ", age=" + age +
                ", head_photo=" + Arrays.toString(head_photo) +
                ", sex='" + sex + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
