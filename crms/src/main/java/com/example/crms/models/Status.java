package com.example.crms.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Status")
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Status_Id")
    private Integer statusId;

    @Column(name = "Status_Message", nullable = false, length = 50)
    private String statusMessage;

    // Constructors, getters, and setters
}