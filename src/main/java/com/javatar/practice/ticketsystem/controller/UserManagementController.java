package com.javatar.practice.ticketsystem.controller;

import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("userMng")

public class UserManagementController {

    @Autowired
    private UserService userService;

    @GetMapping

    public List<User> showAllUser(){
        return userService.GetAllUser();
    }
}
