package com.javatar.practice.ticketsystem.data;

import lombok.Data;

import java.util.Date;

@Data
public class TicketViewModel {

    private int ID;
    private String Subject;

    private Date LastChangeDate;
    private String LastChangeDateShamsi;

    private int StatusID;
    private String StatusTitle;


}
