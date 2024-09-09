package com.javatar.practice.ticketsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tb_Role")
public class Role {
    @Id
  private Integer id;
    @Enumerated(EnumType.STRING)
    private ERole title;



}
