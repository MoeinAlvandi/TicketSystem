package com.javatar.practice.ticketsystem.controller;


import com.javatar.practice.ticketsystem.data.NewTicketRequest;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserManagementTicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("AddNewTicket")
    public String AddNewTicket(@RequestBody NewTicketRequest model){
        try{
            if(!model.getTitle().equals("")){
                //shart true
                //continue
                if(!model.getDescription().equals(""))
                {
                    //shart true
                    //continue
                    //check enter
                    //<script>alert()</script>
                    Ticket ticket= new Ticket(model.getTitle(), model.getDescription());
                    ticketService.InsertTicket(ticket);
                    return "Your Ticket Saved Successfully";
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


}
