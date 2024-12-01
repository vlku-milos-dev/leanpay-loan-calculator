package com.example.leanpay.loan;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LoanRequest(
        @NotNull(message = "Loan amount is required")
        @DecimalMin(value = "1.00", message = "Loan amount must be at least 1")
        BigDecimal amount,

        @NotNull(message = "Loan annualInterestPercentage is required")
        @DecimalMin(value = "0.0", message = "Loan interest rate must be at least 0%")
        @DecimalMax(value = "50.0", message = "Loan interest rate cannot exceed 50%")
        BigDecimal annualInterestPercentage,

        @NotNull(message = "Loan numberOfMonths is required")
        @Min(value = 1, message = "Loan duration must be at least 1 month")
        @Max(value = 480, message = "Loan duration cannot exceed 480 months")
        Integer numberOfMonths
) {
}
