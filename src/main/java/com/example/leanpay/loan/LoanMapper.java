package com.example.leanpay.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanMapper {
    public Loan requestToEntity(LoanRequest request) {
        return Loan.builder()
                .amount(request.amount())
                .annualInterestPercentage(request.annualInterestPercentage())
                .numberOfMonths(request.numberOfMonths())
                .build();
    }
}
