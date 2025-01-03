package com.javatar.practice.ticketsystem.service;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.javatar.practice.ticketsystem.data.AdminTicketViewModel;
import com.javatar.practice.ticketsystem.data.TicketViewModel;
import com.javatar.practice.ticketsystem.model.Message;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.MessageRepository;
import com.javatar.practice.ticketsystem.repository.TicketRipository;
import com.javatar.practice.ticketsystem.repository.TicketStatusRepository;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    private MessageRepository messageRepository;

    public void InsertTicket(Ticket ticket,int UserID)
    {
        Optional<TicketStatus> ticketStatus=ticketStatusRepository.findById(3);
        ticket.setTicketStatus(ticketStatus.get());

        Optional<User> user =userRepository.findById(UserID);
        ticket.setUser(user.get());

        ticket.setCreateDate(new Date());
        ticket.setLastChangeDate(new Date());

        ticketRipository.save(ticket);


        Message message = new Message();
        message.setMessageText(ticket.getDescription());
        message.setCreateDate(new Date());
        message.setLastChangeDate(new Date());
        message.setUserID(UserID);

        message.setTicket(ticket);

        messageRepository.save(message);


    }

    public List<TicketViewModel> GetAllTicket_ByUser(User user) throws ParseException {

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


    public Optional<Ticket> GetTicket_ByID(int TicketID){
        return ticketRipository.findById(TicketID);
    }

    public void UpdateTicket(Ticket ticket)
    {
        ticketRipository.save(ticket);
    }

    public List<AdminTicketViewModel> GetAllAdminTicket_ByStatus(TicketStatus ticketStatus) throws ParseException {

        List<Ticket> tickets=null;
        if(ticketStatus!=null)
        {
            tickets=ticketRipository.getTicketsByTicketStatus(ticketStatus);//open
        }else
        {
            tickets=ticketRipository.findAll();
        }


        if(tickets!=null)
        {
            List<AdminTicketViewModel> result=new ArrayList<>();

            AdminTicketViewModel tmp;

            for(Ticket ticket:tickets){
                tmp=new AdminTicketViewModel();

                tmp.setTicketID(ticket.getId());

                tmp.setUserName(ticket.getUser().getUsername());

                Message message=messageRepository.findFirstByTicket_OrderByCreateDateDesc(ticket);
                if(message!=null)
                {
                    tmp.setLastMessage(message.getMessageText());
                }
                else
                {
                    tmp.setLastMessage("Not found Any Message");
                }



                LocalDate localDate = ticket.getCreateDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                tmp.setSendTime(DateConvertor.DiffCalc(localDate));
                result.add(tmp);
            }
        return result;

        }
        else {
            //agar null bood

            return null;
        }


    }

}
