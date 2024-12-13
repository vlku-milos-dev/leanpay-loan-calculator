package com.example.leanpay.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findLoanByAmountAndAnnualInterestPercentageAndNumberOfMonths(BigDecimal amount, BigDecimal annualInterestPercentage, Integer numberOfMonths);
}
