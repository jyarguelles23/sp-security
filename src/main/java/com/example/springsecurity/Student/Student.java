package com.example.springsecurity.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class Student {

    private final Integer studentId;
    private final String studentName;

    public Student(Integer studentId,String studentName){
        this.studentId=studentId;
        this.studentName=studentName;
    }

}
