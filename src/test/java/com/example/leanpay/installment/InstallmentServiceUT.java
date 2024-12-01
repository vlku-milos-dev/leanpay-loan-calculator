package com.example.leanpay.installment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstallmentServiceUT {

    private final InstallmentService installmentService = new InstallmentService();

    @Test
    void createLoanInstallments_ShouldCreateInstallmentPlan() {
        // Arrange
        int numberOfMonths = 10;
        Installment expectedInstallment = Installment.builder()
                .monthNumber(1)
                .paymentAmount(BigDecimal.valueOf(102.31))
                .principalAmount(BigDecimal.valueOf(98.14))
                .interestAmount(BigDecimal.valueOf(4.17))
                .balanceOwned(BigDecimal.valueOf(901.86))
                .build();

        // Act
        List<Installment> actualInstallments = installmentService.calculateInstallments(
                BigDecimal.valueOf(1000.00), BigDecimal.valueOf(5.0), numberOfMonths);

        // Assert
        assertEquals(numberOfMonths, actualInstallments.size());
        assertEquals(expectedInstallment, actualInstallments.get(0));
    }
}