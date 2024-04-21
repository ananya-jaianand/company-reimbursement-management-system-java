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
public class EmployerController {

    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final ReimbursementService reimbursementService;
    private final StatusService statusService;
    private final PaymentService paymentService;

    private final FilterService filterService;
    private final AllFilterService allFilterService;


    @Autowired
    public EmployerController(EmployeeService employeeService, CategoryService categoryService, ReimbursementService reimbursementService,StatusService statusService,PaymentService paymentService,FilterService filterService, AllFilterService allFilterService) {
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.reimbursementService = reimbursementService;
        this.statusService=statusService;
        this.paymentService=paymentService;
        this.filterService=filterService;
        this.allFilterService=allFilterService;

    }


    // Handler for employer page
    @GetMapping("/employer/{id}")
    public String employerPage(@PathVariable("id") Integer employerId, Model model) {

        model.addAttribute("employer", employeeService.getEmployeeById(employerId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("statuses", statusService.getAllStatus());
        model.addAttribute("employees", employeeService.getAllEmployees(employerId));
        return "employer";

    }


    // Handler for viewing reimbursements by the employer
    @GetMapping("/employer_actions/{id}/viewReimbursements")
    public String viewReimbursements(@PathVariable("id") Integer employerId,
                                     @RequestParam(value = "categoryId", required = false) Integer category,
                                     @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                     @RequestParam(value= "status", required = false) Integer status,
                                     @RequestParam(value ="showAll", required = false) Boolean showAll,
                                     Model model) {
        model.addAttribute("employer", employeeService.getEmployeeById(employerId));
        model.addAttribute("categories", categoryService.getAllCategories());

        Iterable<Reimbursement> reimbursements;

        if (showAll!=null) {
            reimbursements = allFilterService.fetchReimbursements(employerId,status,category,employerId);
        } else {
            reimbursements = filterService.fetchReimbursements( employeeId,status,category,employerId);
        }


        // Get reimbursements based on provided parameters
//        Iterable<Reimbursement> reimbursements = reimbursementService.getReimbursements( employeeId,status,category,employerId);

        // Add reimbursements to the model
        model.addAttribute("reimbursements", reimbursements);

        // Redirect back to the employer page

        return "employer_actions";
    }
    @PostMapping("/employer_actions/{id}/changeReimbursements")
    public String changeReimbursements(@PathVariable("id") Integer employerId,@RequestParam("requestId") Integer requestId,@RequestParam("newStatus")Integer newStatus, Model model){

        if (newStatus == 2) {
            // Get reimbursement information
            Reimbursement reimbursement = reimbursementService.getReimbursementByRequestId(requestId);

            // Get category information
            Category category = reimbursement.getCategory();

            // Calculate refund amount based on category limit and percentage
            double amountRequestedDouble = reimbursement.getAmountRequested().doubleValue();

            double refundAmount = reimbursementService.calculateEmployeeReimbursement(amountRequestedDouble,
                    category.getMaximumLimit(),
                    category.getCategoryPercentage());

            // Create Payment object
            Payment payment = new Payment();
            payment.setRequestId(requestId);
            payment.setPaymentDate(new Date());
            payment.setPaymentAmount(BigDecimal.valueOf(refundAmount));
            payment.setReimbursement(reimbursement);
            // Save Payment
            paymentService.createPayment(payment);
        }
        reimbursementService.changeReimbursementStatus(requestId, newStatus);


        return  "redirect:/employer/" + employerId;
    }


}

