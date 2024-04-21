package com.example.crms.models;

import com.example.crms.repositories.ReimbursementRepository;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Reimbursement")
@Data
public class Reimbursement {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Request_Id")
    private Integer requestId;

    @Column(name = "Date_Submitted", nullable = false)
    private Date dateSubmitted;

    @Column(name = "Category_Id", nullable = false)
    private Integer categoryId;

    @Column(name = "Amount_Requested", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountRequested;

    @Column(name = "Status_Id", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer statusId = 1;

    @Column(name = "Employee_Id", nullable = false)
    private Integer employeeId;

    @Column(name = "Receipt_Id", nullable = false, length = 50)
    private String receiptId;

    @Column(name = "Document_Date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date documentDate;

    @Column(name = "Vendor_Name", nullable = false, length = 50)
    private String vendorName;

    @ManyToOne
    @JoinColumn(name = "Employee_Id", referencedColumnName = "Employee_Id", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "Category_Id", referencedColumnName = "Category_Id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "Status_Id", referencedColumnName = "Status_Id", insertable = false, updatable = false)
    private Status status;


}
