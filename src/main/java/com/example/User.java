package com.example;

public class User {
    public String uid;
    public String name;
    public Integer age;

    @Override
    public String toString() {
        return super.toString() + " " + uid + " " + name + " " + age;
    }
}
