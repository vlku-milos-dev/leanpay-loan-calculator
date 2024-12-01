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

    public List<Installment> calculateInstallments(
            BigDecimal amount, BigDecimal annualInterestRate, Integer numberOfMonths) {

        BigDecimal monthlyInterestRate = calculateMonthlyInterestRate(annualInterestRate);
        BigDecimal monthlyPayment =
                calculateMonthlyPayment(amount, monthlyInterestRate, numberOfMonths);

        return generateInstallmentSchedule(amount, monthlyInterestRate, monthlyPayment, numberOfMonths);
    }

    private BigDecimal calculateMonthlyInterestRate(BigDecimal annualInterestRate) {
        return annualInterestRate
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateMonthlyPayment(
            BigDecimal amount, BigDecimal monthlyInterestRate, Integer numberOfMonths) {
        // Formula for calculating monthly payment
        BigDecimal onePlusInterestPowerN = BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfMonths);
        return amount
                .multiply(monthlyInterestRate)
                .multiply(onePlusInterestPowerN)
                .divide(onePlusInterestPowerN.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_EVEN);
    }

    private List<Installment> generateInstallmentSchedule(
            BigDecimal amount,
            BigDecimal monthlyInterestRate,
            BigDecimal monthlyPayment,
            Integer numberOfMonths) {
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
