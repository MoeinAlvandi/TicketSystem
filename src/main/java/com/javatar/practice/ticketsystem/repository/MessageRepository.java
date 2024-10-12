package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
}
