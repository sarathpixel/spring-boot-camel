package com.mk.camel.converters;


import com.mk.camel.model.Student;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

@Converter(loader = true)
public class StudentTypeConverters implements TypeConverters {
    @Converter
    public byte[] toMyOrder(Student student) {

        return student.toString().getBytes();
    }
}
