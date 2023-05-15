package com.project.loanApp.service;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;

import java.util.List;

public interface CustomerService {
    public Integer doLogin(String email, String password);

    public Customer addCustomer(Customer c);

    public Customer updateCustomer(Customer c);

    public List<Customer> getCustomers();

    public Customer getCustomerById(int custId);

    public boolean checkLoanEligibility(int customerId,int loanId);

}
