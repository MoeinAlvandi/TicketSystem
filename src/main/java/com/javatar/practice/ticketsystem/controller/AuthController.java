package com.javatar.practice.ticketsystem.controller;

import com.javatar.practice.ticketsystem.data.SignupRequest;
import com.javatar.practice.ticketsystem.model.ERole;
import com.javatar.practice.ticketsystem.model.Role;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.RoleRepository;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import com.javatar.practice.ticketsystem.security.Jwtutils;
import com.javatar.practice.ticketsystem.security.service.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    Jwtutils jwtutils;
    @PostMapping("signup")
    public User signup (@RequestBody SignupRequest signupRequest) {
User user = new User(
        signupRequest.getUsername(),
        encoder.encode(signupRequest.getPassword()),
        signupRequest.getEmail());
        Set<String> rolesrequst=signupRequest.getRoles();
        Set<Role> roles=new HashSet<>();
        for(String role:rolesrequst){
            if(role.equals("admin")){
                roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());

            } else if (role.equals("user")) {
                roles.add(roleRepository.findByName(ERole.ROLE_USER).get());

            } else if (role.equals("mod")) {
                roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).get());

            }else {
                roles.add(roleRepository.findByName(ERole.ROLE_USER).get());

            }

        }
        user.setRoles(roles);
        return userRepository.save(user);
    }
    @PostMapping("signin")
    public String signin(@RequestBody User signinRequest ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);//login is ...
        String jwt = jwtutils.generateJwtToken(authentication);
        //UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        //List<String> roles=userDetail.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
        return jwt;



    }

}
