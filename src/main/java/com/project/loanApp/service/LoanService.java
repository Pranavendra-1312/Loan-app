package com.project.loanApp.service;

import com.project.loanApp.entity.Loan;

import java.util.List;

public interface LoanService {
    public Loan applyLoan(Loan l);

    public List<Loan> getLoansByCustomerId(int custId);

    public Loan getLoanByLoanId(int loanId);

    public void forCloseLoan(int loanId);
    public List<Loan> getLoans();

    public int getStatus(int loanId);

}
