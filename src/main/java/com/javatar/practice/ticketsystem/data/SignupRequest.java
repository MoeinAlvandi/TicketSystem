package com.javatar.practice.ticketsystem.data;

import com.javatar.practice.ticketsystem.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;

}
