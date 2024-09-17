package com.javatar.practice.ticketsystem.controller;

import com.javatar.practice.ticketsystem.data.SignupRequest;
import com.javatar.practice.ticketsystem.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @PostMapping("signup")
    public User signup (@RequestBody SignupRequest signupRequest) {

    }

}
