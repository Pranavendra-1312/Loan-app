package com.project.loanApp.service;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;
import com.project.loanApp.exception.CustomerAlreadyRegisteredException;
import com.project.loanApp.exception.CustomerNotFoundException;
import com.project.loanApp.repository.CustomerRepository;
import com.project.loanApp.repository.LoanRepository;
import org.apache.catalina.util.CustomObjectInputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Primary
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private LoanRepository loanDao;

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Customer addCustomer(Customer c) {
        Customer customer = customerDao.checkCustomer(c.getEmail(), c.getAdhaar(), c.getPan(), c.getPhone());
        if (customer != null) {
            throw new CustomerAlreadyRegisteredException("Customer Already Registered: " + customer.getId());
        }
        return customerDao.save(c);
    }
    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
        logger.info("Customer Found: " + customerId);
        return customer;
    }
    @Override
    public List<Customer> getCustomers(){
        List<Customer> customers = customerDao.findAll();
        if(customers.isEmpty())
            throw new CustomerNotFoundException("No customers");
        return customers;
    }

    @Override
    public Customer updateCustomer(Customer c){
        Customer customer = customerDao.findById(c.getId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + c.getId()));
        BeanUtils.copyProperties(c, customer);
        return customerDao.save(customer);
    }

    @Override
    public Integer doLogin(String email, String password) {
        Integer customerId = null;
        customerId = customerDao.findCustomerByEmailAndPassword(email, password);
        if(customerId!=null){
            logger.info("Customer: " + customerId + " Logged In Successfully");
            return customerId;
        }
        else{
            throw new CustomerNotFoundException("Customer Not Found: " + customerId);
        }
    }

    @Override
    public boolean checkLoanEligibility(int id,int loanId){
        Customer c = customerDao.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + id));
        Loan l = loanDao.findById(loanId).orElseThrow(() -> new CustomerNotFoundException("Loan Not Found: " + loanId));
        double loan = l.getLoanAmt();
        final double min_salary = 1000;
        final double max_loan_amount = 60*(0.6*c.getSalary());
        return (loan<=max_loan_amount)&&(c.getSalary()>=min_salary);
    }

}
