package com.example.leanpay.loan;

import com.example.leanpay.installment.Installment;
import com.example.leanpay.installment.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final InstallmentService installmentService;

    /**
     * Creates a new loan with calculated installments.
     *
     * @param amount The principal loan amount
     * @param annualInterestPercentage The annual interest rate as a percentage
     * @param numberOfMonths The loan term in months
     * @return The created and persisted Loan entity
     */
    @Transactional
    public Loan createLoan(BigDecimal amount, BigDecimal annualInterestPercentage, Integer numberOfMonths) {

        List<Installment> installments = installmentService.calculateInstallments(
                amount, annualInterestPercentage, numberOfMonths);

        Loan loan = Loan.builder()
                .amount(amount)
                .annualInterestPercentage(annualInterestPercentage)
                .numberOfMonths(numberOfMonths)
                .installments(installments)
                .build();

        loan.getInstallments().forEach(installment -> installment.setLoan(loan));

        return loanRepository.save(loan);
    }
}
