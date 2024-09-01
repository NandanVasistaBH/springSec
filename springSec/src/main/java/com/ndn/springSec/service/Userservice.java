package com.ndn.springSec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ndn.springSec.entity.User;
import com.ndn.springSec.repositry.UserRepo;
@Service
public class Userservice {
    @Autowired
    public UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        return user;
    }    
}
