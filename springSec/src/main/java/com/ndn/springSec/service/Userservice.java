package com.ndn.springSec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ndn.springSec.entity.User;
import com.ndn.springSec.repositry.UserRepo;

@Service
public class Userservice {
    @Autowired
    public UserRepo userRepo;
    
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }  
    
    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if( authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "failure";
    }  
}
