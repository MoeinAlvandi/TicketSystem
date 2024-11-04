package com.javatar.practice.ticketsystem.data;

import com.javatar.practice.ticketsystem.model.User;
import lombok.Data;

@Data
public class AdminMessageViewModel {

    private int id;
    private String messageText;

    private String userName;
    private int userID;

    private String MessageDate;
    private boolean IsAdmin;


}
