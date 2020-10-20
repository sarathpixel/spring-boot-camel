package com.mk.camel.model;

public class Student {

    String name;

    //    @Email
    String email;

    //    @Min(0)
//    @Max(100)
    int age;
    String status;

    public Student(String name, String email, int age) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ", email=" + email +
                ", age=" + age +
                ", status=" + status +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
