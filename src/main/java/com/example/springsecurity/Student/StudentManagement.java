package com.example.springsecurity.Student;

import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping(path="/all")
    //In case you want to secure an specific rest service yo can do it like this instead of config file
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getStudent() {
        return STUDENTS;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('student:write')")
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
