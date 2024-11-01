package com.javatar.practice.ticketsystem.controller;


import com.javatar.practice.ticketsystem.data.AdminTicketViewModel;
import com.javatar.practice.ticketsystem.model.TicketStatus;
import com.javatar.practice.ticketsystem.service.TicketService;
import com.javatar.practice.ticketsystem.service.TicketStatusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
public class AdminManagementController {

    @Autowired
    private TicketStatusService ticketStatusService;

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("GetTickets/{statusID}")
    public ResponseEntity<?> GetTickets(@PathVariable("statusID") Integer statusID) throws ParseException {

        try {
            if(statusID>0){
                //ehtemalan status dorost ferestade

                Optional<TicketStatus> ticketStatus=ticketStatusService.getTicketStatusByID(statusID);
                if(ticketStatus.isPresent()){
                    List<AdminTicketViewModel> result=ticketService.GetAllAdminTicket_ByStatus(ticketStatus.get());
                    return ResponseEntity.ok(result);
                }else {
                    //return hameye ticket

                    List<AdminTicketViewModel> result=ticketService.GetAllAdminTicket_ByStatus(null);
                    return ResponseEntity.ok(result);
                }

            }else {
                //return hameye ticket
                List<AdminTicketViewModel> result=ticketService.GetAllAdminTicket_ByStatus(null);
                return ResponseEntity.ok(result);
            }

        }catch (Exception e) {
            return ResponseEntity.badRequest().body("خطا : " + e.toString());

        }

    }
}


