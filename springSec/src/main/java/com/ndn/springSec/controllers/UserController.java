package com.ndn.springSec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ndn.springSec.entity.User;
import com.ndn.springSec.service.Userservice;


@RestController
public class UserController {
    @Autowired
    private Userservice service;
    @PostMapping("/register")
    public User register(@RequestBody User user){
        System.out.println(user);
        return service.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){
        System.out.println(user);
        return service.verify(user);
    }

    
}
