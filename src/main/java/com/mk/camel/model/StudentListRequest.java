package com.mk.camel.model;

import java.util.List;

public class StudentListRequest {
    List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentListRequest{" +
                "students=" + students +
                '}';
    }
}
