package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.ERole;
import com.javatar.practice.ticketsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
