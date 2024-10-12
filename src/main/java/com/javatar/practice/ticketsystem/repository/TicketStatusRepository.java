package com.javatar.practice.ticketsystem.repository;

import com.javatar.practice.ticketsystem.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus,Integer> {
}
