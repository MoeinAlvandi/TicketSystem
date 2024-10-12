package com.javatar.practice.ticketsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(length = 1000)
    private String messageText;

    private Date createDate;
    private Date lastChangeDate;

    private int userID;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ticket_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Ticket ticket;
}
