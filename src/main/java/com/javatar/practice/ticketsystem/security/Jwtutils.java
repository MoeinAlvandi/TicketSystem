package com.javatar.practice.ticketsystem.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Jwtutils {
    @Value("${ticketsystem.app.jwtSecret}")
    private String jwtSecret;
    @Value("${ticketsystem.app.jwtExpirationMs}")
    private int jwtexpritionMs;

}
