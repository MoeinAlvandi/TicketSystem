package com.javatar.practice.ticketsystem.service;

import com.javatar.practice.ticketsystem.data.MessageViewModel;
import com.javatar.practice.ticketsystem.model.Message;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.MessageRepository;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MessageViewModel> GetAllMessages_ByTicketID(Ticket ticket) throws ParseException {
        List<Message> AllTicketMessage=messageRepository.findAllByTicket_OrderByCreateDate(ticket);
        List<MessageViewModel> result=new ArrayList<>();
        MessageViewModel tmp;
        for(Message m:AllTicketMessage){
          tmp=new MessageViewModel();
          tmp.setID(m.getId());
          tmp.setMessageContent(m.getMessageText());
          tmp.setCreateDate(m.getCreateDate());
          tmp.setCreateDateShamsi(DateConvertor.toShamsi_withTime(m.getCreateDate().toString()));

          Optional<User> user=userRepository.findById(m.getUserID());
          tmp.setUserID(user.get().getId());
          tmp.setUserName(user.get().getUsername());
          result.add(tmp);
        }
        return result;
    }

    public void InsertMessage(Message message) throws ParseException {
        messageRepository.save(message);
    }
}
