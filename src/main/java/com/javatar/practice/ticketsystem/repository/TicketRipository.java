package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRipository extends JpaRepository<Ticket,Integer> {
}
