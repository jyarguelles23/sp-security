package com.example.springsecurity.Student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagement {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3, "Anna Smith")
    );

    @GetMapping(path="/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student "+ studentId + " does not exist"));
    }

    @GetMapping(path="/all")
    public List<Student> getStudent() {
        return STUDENTS;
    }

    @PostMapping()
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student.getStudentName());
    }

    @DeleteMapping(path = "/delete/{id}")
    public void registerNewStudent(@PathVariable("id") Integer id){
        System.out.println(id);
    }

    @PutMapping(path ="/update")
    public void updateStudent (@RequestBody Student student){
        System.out.println(student.getStudentName());
    }

}
