package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.Message;
import com.javatar.practice.ticketsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {


    List<Message> findAllByTicket_OrderByCreateDate(Ticket ticket);

    Message findFirstByTicket_OrderByCreateDateDesc(Ticket ticket);
}
