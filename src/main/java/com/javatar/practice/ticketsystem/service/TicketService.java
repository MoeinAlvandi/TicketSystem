package com.javatar.practice.ticketsystem.service;

import com.javatar.practice.ticketsystem.data.TicketViewModel;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.TicketRipository;
import com.javatar.practice.ticketsystem.repository.TicketStatusRepository;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRipository ticketRipository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public void InsertTicket(Ticket ticket,int UserID)
    {
        Optional<TicketStatus> ticketStatus=ticketStatusRepository.findById(1);
        ticket.setTicketStatus(ticketStatus.get());

        Optional<User> user =userRepository.findById(UserID);
        ticket.setUser(user.get());
        ticket.setCreateDate(new Date());
        ticket.setLastChangeDate(new Date());

        ticketRipository.save(ticket);
    }

    public List<TicketViewModel> GetAllTicket_ByUserID(User user) throws ParseException {

        List<Ticket> tickets=ticketRipository.getTicketByUser(user);
        List<TicketViewModel> result=new ArrayList<>();
        TicketViewModel tmp;
        for(Ticket ticket:tickets)
        {
            tmp=new TicketViewModel();
            tmp.setID(ticket.getId());
            tmp.setSubject(ticket.getSubject());
            TicketStatus ticketStatus=ticket.getTicketStatus();
            tmp.setStatusID(ticketStatus.getId());
            tmp.setStatusTitle(ticketStatus.getTitle());

            tmp.setLastChangeDate(ticket.getLastChangeDate());
            String tmpShamsi=DateConvertor.toShamsi_withTime(ticket.getLastChangeDate().toString());
            tmp.setLastChangeDateShamsi(tmpShamsi);
            result.add(tmp);
        }


        return result;
    }

}
