package com.ndn.springSec.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ndn.springSec.entity.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController{

    List<Student> list = new ArrayList<>(List.of((new Student("nandan"))));
    @GetMapping("/student")
    public List<Student> getAllStudents(){
        return list;
    }
    @GetMapping("/csrf-token")
    public CsrfToken getCSRF(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
    }
    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        list.add(student);
        System.out.println(student);
        return student;
    }
    

}