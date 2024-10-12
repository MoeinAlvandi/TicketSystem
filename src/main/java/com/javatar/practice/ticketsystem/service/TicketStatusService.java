package com.javatar.practice.ticketsystem.service;

import com.javatar.practice.ticketsystem.repository.TicketStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketStatusService {

    @Autowired
    private TicketStatusRepository ticketStatusRepository;



}
