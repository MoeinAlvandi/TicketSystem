package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRipository extends JpaRepository<Ticket,Integer> {

    List<Ticket> getTicketByUser(User user);
}
