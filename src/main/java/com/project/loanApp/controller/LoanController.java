package com.project.loanApp.controller;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;
import com.project.loanApp.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired(required = true)
    private LoanService loanService;
//    @PostMapping
//    public ResponseEntity<Loan> applyLoan(@RequestBody Loan loan) {
//        return new ResponseEntity<Loan>(loanService.applyLoan(loan), HttpStatus.OK);
//    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable int id) {
        return new ResponseEntity<List<Loan>>(loanService.getLoansByCustomerId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanByLoanId(@PathVariable int id) {
        return new ResponseEntity<Loan>(loanService.getLoanByLoanId(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getLoans() {
        return new ResponseEntity<List<Loan>>(loanService.getLoans(), HttpStatus.OK);
    }
    @DeleteMapping("/forclose/{loanId}")
    public ResponseEntity<String> forecloseLoan(@PathVariable int loanId) {
        loanService.forCloseLoan(loanId);
        return new ResponseEntity<String>("Loan Foreclosed with Loan Id: " + loanId, HttpStatus.OK);
    }

    @GetMapping("/status/{loanId}")
    public ResponseEntity<String> getStatus(@PathVariable int loanId){
        int status = loanService.getStatus(loanId);
        if(status==1)
            return new ResponseEntity<String>("Loan with Loan Id: " + loanId+", is accepted", HttpStatus.OK);
        else
            return new ResponseEntity<String>("Loan with Loan Id: " + loanId+", is rejected", HttpStatus.OK);
    }
}
