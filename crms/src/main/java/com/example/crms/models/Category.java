package com.example.crms.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_Id")
    private Integer categoryId;

    @Column(name = "Category_Name", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "Maximum_Limit", nullable = false)
    private Integer maximumLimit;

    @Column(name = "Category_Percentage", nullable = false)
    private Integer categoryPercentage;

    // Constructors, getters, and setters
}