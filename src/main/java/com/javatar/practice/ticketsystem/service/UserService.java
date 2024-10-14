package com.javatar.practice.ticketsystem.service;


import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> GetAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> GetUser_ByUsername(String Username){

        return userRepository.findByUsername(Username);
    }


}
