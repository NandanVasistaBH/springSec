package com.ndn.springSec.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndn.springSec.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
