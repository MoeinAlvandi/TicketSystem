package com.javatar.practice.ticketsystem.service;

import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.TicketRipository;
import com.javatar.practice.ticketsystem.repository.TicketStatusRepository;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRipository ticketRipository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public void InsertTicket(Ticket ticket)
    {
        Optional<TicketStatus> ticketStatus=ticketStatusRepository.findById(1);
        ticket.setTicketStatus(ticketStatus.get());

        Optional<User> user =userRepository.findById(1);
        ticket.setUser(user.get());
        ticket.setCreateDate(new Date());
        ticket.setLastChangeDate(new Date());

        ticketRipository.save(ticket);
    }
}
