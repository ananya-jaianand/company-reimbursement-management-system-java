OOADJ project - using springboot and thymeleaf

# Company Reimbursement Management System

The Company Reimbursement Management System is designed to streamline the process of handling reimbursement requests within an organization. This project caters to various roles including employees, employers, and administrators, ensuring a smooth and efficient reimbursement process.

## Tech Stack used
- Language used - Java , HTML
- Springboot 
- Thyme Leaf

## Features

### Employee Role:
- **Initiate Reimbursement Requests:**
  - Submit details such as category, amount requested, receipt ID, document date, and vendor name.
- **Track Reimbursements:**
  - View previous reimbursement requests.
  - Monitor the status of submitted requests.

### Employer Role:
- **Process Reimbursement Requests:**
  - Approve or deny reimbursement requests.
  - View detailed information of requests including request ID, date submitted, category, amount requested, receipt ID, document date, and vendor name.
- **Filter Requests:**
  - Filter reimbursement requests based on category, employee ID, and status.

### Administrator Role:
- **Manage Employee Accounts:**
  - Add new employees across the organization.
  - Manage existing employee accounts.

### Calculate Refunds:
  - Perform intricate calculations for refunds on approved reimbursements.
  - Consider category limits and percentages in refund calculations.

## Goals
- **Automation:**
  - Automate the reimbursement process to reduce manual effort.
- **Transparency:**
  - Provide clear visibility into the status and details of reimbursement requests for employees and employers.
- **Efficiency:**
  - Streamline operations to expedite the approval and processing of reimbursements.

## Usage
- Employees can log in to submit and track their reimbursement requests.
- Employers can review, filter, and process the submitted requests.
- Administrators have overarching access to manage employee accounts.
- Automated reimbursment calculation.

## Installation

### Clone the Repository

    ``` 
        git clone https://github.com/ananya-jaianand/company-reimbursement-management-system-java.git

    ```
### Database Setup

1. Open MySQL command line.
2. Create and configure the database:
    ```
        CREATE DATABASE crms;
        USE crms;
        SOURCE <path_to_data.sql>;
        CREATE USER 'abc'@'localhost' IDENTIFIED BY 'password';
        GRANT ALL PRIVILEGES ON crms.* TO 'abc'@'localhost';
    ```
### Running the Project

1. Open the project in IntelliJ IDEA.
2. Build and run the project.



