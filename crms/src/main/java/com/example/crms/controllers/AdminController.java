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
public class AdminController {

    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final ReimbursementService reimbursementService;
    private final StatusService statusService;

    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    @Autowired
    public AdminController(EmployeeService employeeService, CategoryService categoryService, ReimbursementService reimbursementService,StatusService statusService) {
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.reimbursementService = reimbursementService;
        this.statusService=statusService;
    }


    // Handler for admin page
    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin";
    }

    // Handler for adding an employee
    @PostMapping("/admin/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/admin";
    }

//    // Handler for updating an employee
//    @PostMapping("/admin/updateEmployee")
//    public String updateEmployee(@ModelAttribute Employee employee) {
//        employeeService.updateEmployee(employee);
//        return "redirect:/admin";
//    }

    @PostMapping("/admin/updateEmployee")
    public String updateEmployee(@ModelAttribute Employee employee) {
        // Set the manager ID from the form submission
        Integer managerId = employee.getManagerId();
        if (managerId != null) {
            Employee manager = employeeService.getEmployeeById(managerId);
            if (manager != null) {
                employee.setManager(manager);
            }
        }
        employeeService.updateEmployee(employee);
        return "redirect:/admin";
    }

    // Handler for deleting an employee
//    @GetMapping("/admin/deleteEmployee/{id}")
//    public String deleteEmployee(@PathVariable("id") Integer employeeId) {
//        employeeService.deleteEmployee(employeeId);
//        return "redirect:/admin";
//    }

    @GetMapping("/admin/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer employeeId) {
        // Get the employee being deleted
        Employee deletedEmployee = employeeService.getEmployeeById(employeeId);

        // Get the employees managed by the employee being deleted
        List<Employee> employeesManagedByDeletedEmployee = employeeService.getEmployeesByManagerId(employeeId);

        // Update manager ID for employees managed by the employee being deleted
        for (Employee employee : employeesManagedByDeletedEmployee) {
            employee.setManagerId(null);
            employeeService.updateEmployee(employee);
        }

        // Delete the employee
        employeeService.deleteEmployee(employeeId);

        // Notify other employees of the deletion
        employeeService.notifyEmployees(deletedEmployee);

        return "redirect:/admin";
    }



    // New controller method for displaying the update form
    @GetMapping("/admin/updateEmployee/{id}")
    public String showUpdateEmployeeForm(@PathVariable("id") Integer id, Model model) {
        // Retrieve the employee with the given ID
        Employee employee = employeeService.getEmployeeById(id);
        // Add the employee object to the model
        model.addAttribute("employee", employee);
        // Return the name of the Thymeleaf template for rendering the update form
        return "update_employee_form";
    }




}

