package com.javatar.practice.ticketsystem.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javatar.practice.ticketsystem.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailImpl implements UserDetails {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    public Collection<? extends GrantedAuthority> authorities;

    public UserDetailImpl(Integer id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }
    public static UserDetailImpl build(User user) {
        User userDetail = new User();
        userDetail.setId(user.getId());
        userDetail.setUsername(user.getUsername());
        userDetail.setPassword(user.getPassword());
        userDetail.setEmail(user.getEmail());

        User user1 =User.builder().id(1).username("sss").password("ssf").email("sd").build();



        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle().name()))
                .collect(Collectors.toList());

        return new UserDetailImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
