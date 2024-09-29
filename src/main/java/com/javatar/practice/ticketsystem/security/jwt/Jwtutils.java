package com.javatar.practice.ticketsystem.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.javatar.practice.ticketsystem.security.service.UserDetailImpl;

import java.security.Key;
import java.util.Date;

@Component
public class Jwtutils {
    @Value("${ticketsystem.app.jwtSecret}")
    private String jwtSecret;
    @Value("${ticketsystem.app.jwtExpirationMs}")
    private int jwtexpritionMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailImpl details =(UserDetailImpl) authentication.getPrincipal(); // UserDetailsImpl
        return Jwts.builder()
                .setSubject(details.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + jwtexpritionMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUsernameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody()
                .getSubject();
    }
    public boolean validationJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build()
                    .parse(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
