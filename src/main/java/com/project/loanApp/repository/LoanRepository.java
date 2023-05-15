package com.project.loanApp.repository;

import java.util.List;

import com.project.loanApp.entity.Customer;
import com.project.loanApp.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    @Query("select l from Loan l where l.id=?1")
    Customer findByCustomerId(int custId);
}