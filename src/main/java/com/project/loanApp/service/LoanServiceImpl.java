package com.project.loanApp.service;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;
import com.project.loanApp.exception.CustomerNotFoundException;
import com.project.loanApp.exception.LoanNotFoundException;
import com.project.loanApp.repository.CustomerRepository;
import com.project.loanApp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import org.apache.log4j.Logger;

@Service
@Primary
public class LoanServiceImpl implements LoanService{
    @Autowired
    private LoanRepository loanDao;

    @Autowired
    private CustomerRepository customerDao;

    @Autowired
    private CustomerService customerService;
    private Logger logger = Logger.getLogger(getClass());
    @Override
    public Loan applyLoan(Loan loan) {
        int customerId = loan.getCustomer().getId();

        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cusotmer Not Found: " + customerId));
        customer.addLoan(loan);
        return loanDao.save(loan);
    }

    @Override
    public List<Loan> getLoansByCustomerId(int customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
        return customer.getLoans();
    }

    @Override
    public Loan getLoanByLoanId(int loanId){
        Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
        return loan;
    }

    @Override
    public void forCloseLoan(int loanId) {
        Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
        loanDao.delete(loan);
    }

    @Override
    public List<Loan> getLoans(){
        List<Loan> loans = loanDao.findAll();
        if(loans.isEmpty())
            throw new LoanNotFoundException("No loans");
        return loans;
    }

    @Override
    public int getStatus(int loanId){
        Loan l = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
        boolean isEligible = customerService.checkLoanEligibility(l.getCustomer().getId(),loanId);
        if(isEligible)
            return 1;
        else
            return 0;

    }
}
