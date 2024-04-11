package com.example.crms.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_Id")
    private Integer paymentId;

    @Column(name = "Request_Id", nullable = false)
    private Integer requestId;

    @Column(name = "Payment_Date", nullable = false)
    private Date paymentDate;

    @Column(name = "Payment_Amt", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @ManyToOne
    @JoinColumn(name = "Request_Id", referencedColumnName = "Request_Id", insertable = false, updatable = false)
    private Reimbursement reimbursement;

    // Constructors, getters, and setters
}