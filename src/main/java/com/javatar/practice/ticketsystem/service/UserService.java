package com.javatar.practice.ticketsystem.service;


import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> GetAllUser(){
        return userRepository.findAll();
    }

}
