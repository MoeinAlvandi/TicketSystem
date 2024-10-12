package com.javatar.practice.ticketsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
public class TicketStatus {

    @Id
    private int Id;

    @Column(length = 50)
    private String Title;


}
