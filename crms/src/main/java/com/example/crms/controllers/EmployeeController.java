package com.example.crms.controllers;

import com.example.crms.models.*;
import com.example.crms.services.*;
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
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final ReimbursementService reimbursementService;
    private final StatusService statusService;

    private final PaymentService paymentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CategoryService categoryService, ReimbursementService reimbursementService,StatusService statusService,PaymentService paymentService) {
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.reimbursementService = reimbursementService;
        this.statusService=statusService;
        this.paymentService=paymentService;

    }


    // Handler for employee page
    @GetMapping("/employee/{id}")
    public String employeePage(@PathVariable("id") Integer employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        List<Reimbursement> pendingReimbursements = reimbursementService.getPendingReimbursementsByEmployeeId(employeeId);
        model.addAttribute("pendingReimbursements", pendingReimbursements);

//        List<Reimbursement> approvedReimbursements = reimbursementService.getApprovedReimbursementsByEmployeeId(employeeId);
//        model.addAttribute("approvedReimbursements", approvedReimbursements);

        List<Payment> approvedReimbursements = paymentService.getAllPaymentsByEmployeeId(employeeId);
        model.addAttribute("approvedReimbursements", approvedReimbursements);

        List<Reimbursement> deniedReimbursements = reimbursementService.getDeniedReimbursementsByEmployeeId(employeeId);
        model.addAttribute("deniedReimbursements", deniedReimbursements);

        model.addAttribute("employee", employee);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employee";
    }

    @PostMapping("/employee/{id}/edit/{requestId}")
    public String editReimbursementForm(@PathVariable("id") Integer employeeId,
                                        @PathVariable("requestId") Integer requestId,
                                        @RequestParam("categoryId") Integer categoryId,
                                        @RequestParam("amountRequested") BigDecimal amountRequested,
                                        @RequestParam("receiptId") String receiptId,
                                        @RequestParam("documentDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date documentDate,
                                        @RequestParam("vendorName") String vendorName) {

        // Retrieve the existing reimbursement from the database
        Reimbursement existingReimbursement = reimbursementService.getReimbursementByRequestId(requestId);

        // Update the attributes with new values
        existingReimbursement.setCategoryId(categoryId);
        existingReimbursement.setAmountRequested(amountRequested);
        existingReimbursement.setReceiptId(receiptId);
        existingReimbursement.setDocumentDate(documentDate);
        existingReimbursement.setVendorName(vendorName);

        Category category = categoryService.getCategoryById(existingReimbursement.getCategoryId());

        // Set the fetched entities on the reimbursement object
        existingReimbursement.setCategory(category);

        // Save the updated reimbursement back to the database
        reimbursementService.updateReimbursement(existingReimbursement);

        // Redirect back to the employee page
        return "redirect:/employee/" + employeeId;
    }


    @GetMapping("/employee/{id}/edit/{requestId}")
    public String editReimbursement(@PathVariable("id") Integer employeeId,
                                    @PathVariable("requestId") Integer requestId,Model model) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        Reimbursement reimbursement = reimbursementService.getReimbursementByRequestId(requestId);
        model.addAttribute("employee", employee);
        model.addAttribute("reimbursement", reimbursement);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "employee_edit_reimbursement";

    }

    @GetMapping("/employee/{id}/delete/{requestId}")
    public String deleteReimbursement(@PathVariable("id") Integer employeeId,
                                      @PathVariable("requestId") Integer requestId,Model model) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        Reimbursement reimbursement = reimbursementService.getReimbursementByRequestId(requestId);
        model.addAttribute("employee", employee);
        model.addAttribute("reimbursement", reimbursement);
        return "employee_delete_reimbursement";

    }

    @PostMapping("/employee/{id}/delete/{requestId}")
    public String deleteReimbursementForm(@PathVariable("id") Integer employeeId,
                                          @PathVariable("requestId") Integer requestId
    ) {

        // Retrieve the existing reimbursement from the database
        Reimbursement existingReimbursement = reimbursementService.getReimbursementByRequestId(requestId);

        // Save the updated reimbursement back to the database
        reimbursementService.deleteReimbursement(existingReimbursement);

        // Redirect back to the employee page
        return "redirect:/employee/" + employeeId;
    }
    // Handler for creating a reimbursement
    @PostMapping("/employee/{id}/createReimbursement")
    public String createReimbursement(@PathVariable("id") Integer employeeId,@RequestParam("categoryId") Integer categoryId,
                                      @RequestParam("amountRequested") BigDecimal amountRequested,
                                      @RequestParam("receiptId") String receiptId,
                                      @RequestParam("documentDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date documentDate,
                                      @RequestParam("vendorName") String vendorName, @ModelAttribute Reimbursement reimbursement, Model model) {

//        model.addAttribute("categories", categoryService.getAllCategories());
        reimbursement.setEmployeeId(employeeId);
        reimbursement.setCategoryId(categoryId);
        reimbursement.setAmountRequested(amountRequested);
        reimbursement.setReceiptId(receiptId);
        reimbursement.setDocumentDate(documentDate);
        reimbursement.setVendorName(vendorName);
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



}

