package com.javatar.practice.ticketsystem.data;

import lombok.Data;

import java.util.Date;

@Data
public class MessageViewModel {

    private int ID;//messageID
    private String messageContent;

    private Date CreateDate;
    private String CreateDateShamsi;

    private int UserID;
    private String UserName;
}
