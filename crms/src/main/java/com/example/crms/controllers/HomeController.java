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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                model.addAttribute("employer", employeeService.getEmployeeById(employeeId)); // Assuming employer info is retrieved in a similar way as employee
                return "redirect:/employer/"+ employeeId;
            }
        } else {
            // Employee does not exist, return error message
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/";
        }

        // Invalid role
        return "redirect:/";
    }

    // Handler for employee page
    @GetMapping("/employee/{id}")
    public String employeePage(@PathVariable("id") Integer employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employee";
    }


    // Handler for creating a reimbursement
    @PostMapping("/employee/{id}/createReimbursement")
    public String createReimbursement(@PathVariable("id") Integer employeeId, @ModelAttribute Reimbursement reimbursement, Model model) {

//        model.addAttribute("categories", categoryService.getAllCategories());
        reimbursement.setEmployeeId(employeeId);
        reimbursement.setDateSubmitted(new Date());
        reimbursement.setStatusId(1);

        Employee employee = employeeService.getEmployeeById(employeeId);
        Category category = categoryService.getCategoryById(reimbursement.getCategoryId());
        Status status = statusService.getStatusById(reimbursement.getStatusId());

        // Set the fetched entities on the reimbursement object
        reimbursement.setEmployee(employee);
        reimbursement.setCategory(category);
        reimbursement.setStatus(status);


        // Save reimbursement
        reimbursementService.createReimbursement(reimbursement);

        // Add success message
        model.addAttribute("message", "Reimbursement created successfully!");

        // Redirect back to the employee page
        return "redirect:/employee/" + employeeId;
    }

    // Handler for employer page
    @GetMapping("/employer/{id}")
    public String employerPage(@PathVariable("id") Integer employerId, Model model) {

            model.addAttribute("employer", employeeService.getEmployeeById(employerId));
            return "employer";

    }

}

