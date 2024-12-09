package com.example.leanpay.installment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InstallmentService {

    /**
     * Calculates the complete installment schedule for a loan.
     *
     * @param amount The principal loan amount
     * @param annualInterestRate The annual interest rate as a percentage
     * @param numberOfMonths The loan term in months
     * @return List of calculated installments for the entire loan term
     */
    public List<Installment> calculateInstallments(
            BigDecimal amount, BigDecimal annualInterestRate, Integer numberOfMonths) {

        BigDecimal monthlyInterestRate = calculateMonthlyInterestRate(annualInterestRate);
        BigDecimal monthlyPayment =
                calculateMonthlyPayment(amount, monthlyInterestRate, numberOfMonths);

        return generateInstallmentSchedule(amount, monthlyInterestRate, monthlyPayment, numberOfMonths);
    }

    /**
     * Converts annual interest rate to monthly interest rate.
     *
     * @param annualInterestRate The annual interest rate as a percentage
     * @return The calculated monthly interest rate
     */
    private BigDecimal calculateMonthlyInterestRate(BigDecimal annualInterestRate) {
        return annualInterestRate
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);
    }

    /**
     * Calculates the fixed monthly payment amount using the loan amortization formula.
     *
     * @param amount The principal loan amount
     * @param monthlyInterestRate The monthly interest rate
     * @param numberOfMonths The loan term in months
     * @return The calculated fixed monthly payment amount
     */
    private BigDecimal calculateMonthlyPayment(
            BigDecimal amount, BigDecimal monthlyInterestRate, Integer numberOfMonths) {
        // Formula for calculating monthly payment
        BigDecimal onePlusInterestPowerN = BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfMonths);
        return amount
                .multiply(monthlyInterestRate)
                .multiply(onePlusInterestPowerN)
                .divide(onePlusInterestPowerN.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_EVEN);
    }

    /**
     * Generates a complete installment schedule with monthly payment breakdown.
     *
     * @param amount The principal loan amount
     * @param monthlyInterestRate The monthly interest rate
     * @param monthlyPayment The fixed monthly payment amount
     * @param numberOfMonths The loan term in months
     * @return List of installments with detailed payment breakdown
     */
    private List<Installment> generateInstallmentSchedule(
            BigDecimal amount, BigDecimal monthlyInterestRate, BigDecimal monthlyPayment, Integer numberOfMonths) {

        BigDecimal remainingBalance = amount;
        List<Installment> schedule = new ArrayList<>();

        for (int month = 1; month <= numberOfMonths; month++) {
            BigDecimal interestAmount =
                    remainingBalance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_EVEN);

            BigDecimal principalAmount =
                    monthlyPayment.subtract(interestAmount).setScale(2, RoundingMode.HALF_EVEN);

            remainingBalance =
                    remainingBalance.subtract(principalAmount).setScale(2, RoundingMode.HALF_EVEN);

            schedule.add(
                    Installment.builder()
                            .monthNumber(month)
                            .paymentAmount(monthlyPayment)
                            .principalAmount(principalAmount)
                            .interestAmount(interestAmount)
                            .balanceOwned(remainingBalance)
                            .build());
        }

        return schedule;
    }
}
