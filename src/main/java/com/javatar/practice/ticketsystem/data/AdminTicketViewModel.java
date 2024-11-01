package com.javatar.practice.ticketsystem.data;

import lombok.Data;

@Data
public class AdminTicketViewModel {

    private int ticketID;
    private String userName;

    private String sendTime;
    private String lastMessage;

}
