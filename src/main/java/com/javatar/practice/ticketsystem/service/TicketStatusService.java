package com.javatar.practice.ticketsystem.service;

import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.repository.TicketStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TicketStatusService {

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    public Optional<TicketStatus> getTicketStatusByID(int id) {
        return ticketStatusRepository.findById(id);
    }

}
