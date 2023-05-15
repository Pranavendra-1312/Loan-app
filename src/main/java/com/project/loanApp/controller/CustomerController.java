package com.project.loanApp.controller;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;
import com.project.loanApp.service.CustomerService;
import com.project.loanApp.service.LoanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired(required = true)
    CustomerService customerService;
    @Autowired(required = true)
    LoanService loanService;

    private Logger logger = Logger.getLogger(getClass());

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer c) {
        return new ResponseEntity<Customer>(customerService.addCustomer(c), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        return new ResponseEntity<Customer>(customerService.getCustomerById(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<List<Customer>>(customerService.getCustomers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer c) {
        return new ResponseEntity<Customer>(customerService.updateCustomer(c), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> doLogin(@RequestParam String email, @RequestParam String password) {
        return new ResponseEntity<Integer>(customerService.doLogin(email, password), HttpStatus.OK);
    }

    @PostMapping("/calculateEligibility")
    public ResponseEntity<String> checkLoanEligibility(@RequestParam int id, @RequestParam int loanId) {
//        return new ResponseEntity<Boolean>(customerService.checkLoanEligibility(id, loan), HttpStatus.OK);
        boolean isEligible = customerService.checkLoanEligibility(id,loanId);
        if(isEligible){
            return ResponseEntity.ok("Congratulations! You are eligible for the loan");
        }
        else{
            return ResponseEntity.badRequest().body("Sorry, You are not eligible for the loan");
        }
    }
    @PostMapping("/{customerId}/applyloan")
    public ResponseEntity<String> applyLoan(@PathVariable int customerId, @RequestBody Loan loan) {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer == null) {
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        }
        loan.setCustomer(customer);
        loanService.applyLoan(loan);
        return new ResponseEntity<String>("Loan applied successfully", HttpStatus.OK);
    }
    
}
