package com.javatar.practice.ticketsystem.controller;


import com.javatar.practice.ticketsystem.data.NewTicketRequest;
import com.javatar.practice.ticketsystem.data.TicketViewModel;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import com.javatar.practice.ticketsystem.security.jwt.Jwtutils;
import com.javatar.practice.ticketsystem.service.TicketService;
import com.javatar.practice.ticketsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping()
public class UserManagementTicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    private UserService userService;


    @PostMapping("AddNewTicket")
    public String AddNewTicket(@RequestHeader("Authorization") String authHeader, @RequestBody NewTicketRequest model){
        try{

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return "Authorization header is missing or invalid";
            }
            String token = authHeader.substring(7);

            if(!model.getTitle().equals("")){
                //shart true
                //continue
                if(!model.getDescription().equals(""))
                {
                    //shart true
                    //continue
                    //check enter
                    //<script>alert()</script>

                    String Username= jwtutils.getUsernameFromJwtToken(token);
                    if(!Username.equals(""))
                    {
                        User user =userService.GetUser_ByUsername(Username).get();

                        if(user!=null)
                        {
                            Ticket ticket= new Ticket(model.getTitle(), model.getDescription());

                            ticketService.InsertTicket(ticket,user.getId());
                            return "Your Ticket Saved Successfully";
                        }
                        else
                        {
                            return "Not Authenticate";
                        }

                    }
                    else
                    {
                        return "Not Authenticate";
                    }

                }
                else
                {
                    return "Please Enter description";
                }
            }
            else {
                return "Please Enter Title";
            }
        }
        catch (Exception ex){
            return ex.toString();
        }
    }


    @GetMapping("getUserTickets")
    public List<TicketViewModel> ShowUserTicket(@RequestHeader("Authorization") String authHeader) throws ParseException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);

        String Username= jwtutils.getUsernameFromJwtToken(token);
        if(!Username.equals(""))
        {
            User user =userService.GetUser_ByUsername(Username).get();

            if(user!=null)
            {

                List<TicketViewModel> result=ticketService.GetAllTicket_ByUserID(user);

                return result;
            }
            else
            {
                return null;
            }

        }
        else
        {
            return null;
        }
    }
}
