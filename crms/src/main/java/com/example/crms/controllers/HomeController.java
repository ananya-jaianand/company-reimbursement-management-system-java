package com.example.crms.controllers;

import com.example.crms.models.Category;
import com.example.crms.models.Employee;
import com.example.crms.models.Reimbursement;
import com.example.crms.models.Status;
import com.example.crms.services.CategoryService;
import com.example.crms.services.EmployeeService;
import com.example.crms.services.ReimbursementService;
import com.example.crms.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final ReimbursementService reimbursementService;
    private final StatusService statusService;


    @Autowired
    public HomeController(EmployeeService employeeService, CategoryService categoryService, ReimbursementService reimbursementService,StatusService statusService) {
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.reimbursementService = reimbursementService;
        this.statusService=statusService;
    }

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Company Reimbursement Management System");
        return "index";
    }

    // Login handler
    @PostMapping("/login")
    public String login(@RequestParam("role") String role, @RequestParam("id") String id, RedirectAttributes redirectAttributes, Model model) {
        // Check if the employee exists in the database
        Integer employeeId = Integer.parseInt(id);
        if (employeeService.existsById(employeeId)) {
            // Redirect to employee/employer page with the specific employee ID
            if ("employee".equals(role)) {
                model.addAttribute("employee", employeeService.getEmployeeById(employeeId));
                return "redirect:/employee/"+ employeeId;
            } else if ("employer".equals(role)) {
                if (employeeService.getEmployerById(employeeId) != null){
                model.addAttribute("employer", employeeService.getEmployerById(employeeId)); // Assuming employer info is retrieved in a similar way as employee
                return "redirect:/employer/"+ employeeId;}
            }
        } else {
            // Employee does not exist, return error message
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/";
        }

        // Invalid role
        return "redirect:/";
    }





}

