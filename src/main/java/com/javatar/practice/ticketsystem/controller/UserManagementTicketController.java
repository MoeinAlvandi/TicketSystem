package com.javatar.practice.ticketsystem.controller;


import com.javatar.practice.ticketsystem.data.MessageViewModel;
import com.javatar.practice.ticketsystem.data.NewMessageViewModel;
import com.javatar.practice.ticketsystem.data.NewTicketRequest;
import com.javatar.practice.ticketsystem.data.TicketViewModel;
import com.javatar.practice.ticketsystem.model.Message;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.repository.UserRepository;
import com.javatar.practice.ticketsystem.security.jwt.Jwtutils;
import com.javatar.practice.ticketsystem.service.MessageService;
import com.javatar.practice.ticketsystem.service.TicketService;
import com.javatar.practice.ticketsystem.service.TicketStatusService;
import com.javatar.practice.ticketsystem.service.UserService;
import com.mysql.cj.x.protobuf.Mysqlx;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
public class UserManagementTicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TicketStatusService ticketStatusService;

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

                List<TicketViewModel> result=ticketService.GetAllTicket_ByUser(user);

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

    @GetMapping("getTicketMessage/{ticketID}")
    public ResponseEntity<?> getTicketMessage(@RequestHeader("Authorization") String authHeader, @PathVariable("ticketID") Integer ticketID) throws ParseException {
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
                Optional<Ticket> ticket=ticketService.GetTicket_ByID(ticketID);
                if(ticket.get()!=null)
                {
                    User userTicket=ticket.get().getUser();
                    if(userTicket!=null){
                        if(userTicket==user){
                                //getuserMessage
                            List<MessageViewModel> result=messageService.GetAllMessages_ByTicketID(ticket.get());
                            return ResponseEntity.ok(result);
                        }
                        else {
                            return ResponseEntity.badRequest().body("تیکت نامعتبر است.");
                        }
                    }
                    else {
                        return ResponseEntity.badRequest().body("خطای سیستمی");

                    }

                }
                else
                {
                    return ResponseEntity.badRequest().body("تیکت پیدا نشد");

                }

            }
            else
            {
                return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

            }

        }
        else
        {
            return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

        }

    }

    @PostMapping("AddNewMessage")
    public ResponseEntity<?> AddNewMessage(@RequestHeader("Authorization") String authHeader, @RequestBody NewMessageViewModel model) throws ParseException {
        try{
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("لطفا توکن را به درستی وارد کنید");

            }
            String token = authHeader.substring(7);


            String Username= jwtutils.getUsernameFromJwtToken(token);
            if(!Username.equals(""))
            {
                User user =userService.GetUser_ByUsername(Username).get();

                if(user!=null)
                {
                    Optional<Ticket> ticket=ticketService.GetTicket_ByID(model.getTicketID());
                    if(ticket.get()!=null)
                    {

                        Optional<TicketStatus> status=ticketStatusService.getTicketStatusByID(2);

                        if(ticket.get().getTicketStatus()!=status.get())
                        {
                            //moshkel nist
                            User userTicket=ticket.get().getUser();
                            if(userTicket!=null){
                                if(userTicket==user){
                                    Message message= new Message();
                                    message.setTicket(ticket.get());
                                    message.setMessageText(model.getMessageText());
                                    message.setUserID(user.getId());
                                    message.setCreateDate(new Date());
                                    message.setLastChangeDate(new Date());

                                    messageService.InsertMessage(message);

                                    Optional<TicketStatus> ticketStatus = ticketStatusService.getTicketStatusByID(3);//wait
                                    ticket.get().setTicketStatus(ticketStatus.get());
                                    ticket.get().setLastChangeDate(new Date());
                                    ticketService.UpdateTicket(ticket.get());


                                    return ResponseEntity.ok().body("اطلاعات با موفقیت ثبت شد");
                                    //return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

                                }
                                else {
                                    return ResponseEntity.badRequest().body("دسترسی غیر مجاز");
                                }
                            }
                            else {
                                return ResponseEntity.badRequest().body("خطای سیستمی");
                            }
                        }
                        else {
                            //close
                            return ResponseEntity.badRequest().body("تیکت مد نظر شما بسته شده است. لطفا تیکت جدید ایجاد نمایید.");

                        }



                    }
                    else
                    {
                        return ResponseEntity.badRequest().body("تیکت پیدا نشد");
                    }

                }
                else
                {
                    return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

                }

            }
            else
            {
                return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

            }
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body("خطای سیستمی"+ " "+ex.toString());
        }


    }

    @GetMapping("CloseTicket/{ticketID}")
    public ResponseEntity<?> CloseTicket(@RequestHeader("Authorization") String authHeader, @PathVariable("ticketID") Integer ticketID) throws ParseException {
        try{
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("لطفا توکن را به درستی وارد کنید");

            }
            String token = authHeader.substring(7);


            String Username= jwtutils.getUsernameFromJwtToken(token);
            if(!Username.equals(""))
            {
                User user =userService.GetUser_ByUsername(Username).get();

                if(user!=null)
                {
                    Optional<Ticket> ticket=ticketService.GetTicket_ByID(ticketID);
                    if(ticket.get()!=null)
                    {

                        Optional<TicketStatus> statusClose=ticketStatusService.getTicketStatusByID(2);//close

                        if(ticket.get().getTicketStatus()!=statusClose.get())
                        {
                            //moshkel nist
                            User userTicket=ticket.get().getUser();
                            if(userTicket!=null){
                                if(userTicket==user){

                                    ticket.get().setTicketStatus(statusClose.get());
                                    ticketService.UpdateTicket(ticket.get());

                                    return ResponseEntity.ok().body("اطلاعات با موفقیت ثبت شد");
                                    //return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

                                }
                                else {
                                    return ResponseEntity.badRequest().body("دسترسی غیر مجاز");
                                }
                            }
                            else {
                                return ResponseEntity.badRequest().body("خطای سیستمی");
                            }
                        }
                        else {
                            //close
                            return ResponseEntity.badRequest().body("تیکت مد نظر شما بسته شده است.");

                        }



                    }
                    else
                    {
                        return ResponseEntity.badRequest().body("تیکت پیدا نشد");
                    }

                }
                else
                {
                    return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

                }

            }
            else
            {
                return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

            }
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body("خطای سیستمی"+ " "+ex.toString());
        }
    }
}
