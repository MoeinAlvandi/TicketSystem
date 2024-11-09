package com.javatar.practice.ticketsystem.controller;


import com.javatar.practice.ticketsystem.data.AdminMessageViewModel;
import com.javatar.practice.ticketsystem.data.AdminTicketViewModel;
import com.javatar.practice.ticketsystem.data.AswerTicketViewModel;
import com.javatar.practice.ticketsystem.data.NewMessageViewModel;
import com.javatar.practice.ticketsystem.model.Message;
import com.javatar.practice.ticketsystem.model.Ticket;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.model.User;
import com.javatar.practice.ticketsystem.security.jwt.Jwtutils;
import com.javatar.practice.ticketsystem.service.MessageService;
import com.javatar.practice.ticketsystem.service.TicketService;
import com.javatar.practice.ticketsystem.service.TicketStatusService;
import com.javatar.practice.ticketsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
public class AdminManagementController {

    @Autowired
    private TicketStatusService ticketStatusService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private Jwtutils jwtutils;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("GetTicketsAdmin")
    public ResponseEntity<?> GetTicketsAdmin(@RequestParam("statusID") Integer statusID) throws ParseException {

        try {
            if (statusID > 0) {
                //ehtemalan status dorost ferestade

                Optional<TicketStatus> ticketStatus = ticketStatusService.getTicketStatusByID(statusID);
                if (ticketStatus.isPresent()) {
                    List<AdminTicketViewModel> result = ticketService.GetAllAdminTicket_ByStatus(ticketStatus.get());
                    return ResponseEntity.ok(result);
                } else {
                    //return hameye ticket

                    List<AdminTicketViewModel> result = ticketService.GetAllAdminTicket_ByStatus(null);
                    return ResponseEntity.ok(result);
                }

            } else {
                //return hameye ticket
                List<AdminTicketViewModel> result = ticketService.GetAllAdminTicket_ByStatus(null);
                return ResponseEntity.ok(result);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا : " + e.toString());

        }

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("GetMessagesAdmin")
    //QueryString
    public ResponseEntity<?> GetMessagesAdmin(@RequestParam("ticketID") Integer ticketID) throws ParseException {

        try {
            if (ticketID > 0) {

                Optional<Ticket> ticket = ticketService.GetTicket_ByID(ticketID);
                if (ticket.isPresent()) {

                    List<AdminMessageViewModel> result = messageService.GetAllMessages_ByTicketforAdmin(ticket.get());
                    if (result.size() > 0) {
                        return ResponseEntity.ok(result);

                    } else {
                        return ResponseEntity.ok().body("پیامی یافت نشد.");
                    }
                } else {
                    return ResponseEntity.badRequest().body("خطا:تیکت مد نظر یافت نشد.");

                }

            } else {
                return ResponseEntity.badRequest().body("خطا:تیکت مد نظر یافت نشد.");

            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا : " + e.toString());

        }

    }





    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("AnswerTicketAdmin")
    //QueryString
    public ResponseEntity<?> AnswerTicketAdmin(@RequestHeader("Authorization") String authHeader, @RequestBody AswerTicketViewModel model) throws ParseException {

        try {
          //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("لطفا توکن را به درستی وارد کنید");

            }
            String token = authHeader.substring(7);

            String Username = jwtutils.getUsernameFromJwtToken(token);
            if (!Username.equals("")) {
                User user = userService.GetUser_ByUsername(Username).get();

                if (user != null) {//useri k login karde moshakhas shod


                    Optional<Ticket> ticket = ticketService.GetTicket_ByID(model.getTicketId());
                    if (ticket.get() != null) {

                        if (model.getMessageText() != "") {

                            Message message= new Message();
                            message.setTicket(ticket.get());
                            message.setMessageText(model.getMessageText());
                            message.setUserID(user.getId());
                            message.setCreateDate(new Date());
                            message.setLastChangeDate(new Date());

                            messageService.InsertMessage(message);

                            Optional<TicketStatus> ticketStatus = ticketStatusService.getTicketStatusByID(1);//open ya pasokh dade shod
                            ticket.get().setTicketStatus(ticketStatus.get());
                            ticket.get().setLastChangeDate(new Date());
                            ticketService.UpdateTicket(ticket.get());

                            return ResponseEntity.ok().body("اطلاعات با موفقیت ثبت شد");


                        } else {
                            return ResponseEntity.badRequest().body("لطفا متن پیام را وارد کنید");

                        }


                    } else {
                        return ResponseEntity.badRequest().body("تیکت پیدا نشد");
                    }

                } else {
                    return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

                }

            } else {
                return ResponseEntity.badRequest().body("دسترسی غیر مجاز");

            }


        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا : " + e.toString());

        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("CloseTicketByAdmin/{ticketID}")
    public ResponseEntity<?> CloseTicketByAdmin(@PathVariable("ticketID") Integer ticketID) throws ParseException {
        try {
            if (ticketID > 0) {
                Optional<Ticket> ticket = ticketService.GetTicket_ByID(ticketID);
                if (ticket.isPresent()) {
                    Optional<TicketStatus> ticketStatusClose = ticketStatusService.getTicketStatusByID(2);//close
                    if (ticket.get().getTicketStatus().getId() != 2) {
                        //close  nabood
                        ticket.get().setTicketStatus(ticketStatusClose.get());
                        ticket.get().setLastChangeDate(new Date());
                        ticketService.UpdateTicket(ticket.get());
                        return ResponseEntity.ok().body("تیکت با موفقیت بسته شد.");
                    }
                    else {
                        return ResponseEntity.badRequest().body("تیکت شما قبلا بسته شده است.");

                    }

                } else {
                    return ResponseEntity.badRequest().body("تیکت پیدا نشد");

                }
            } else {
                return ResponseEntity.badRequest().body("تیکت پیدا نشد");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا : " + e.toString());
        }

    }

}


