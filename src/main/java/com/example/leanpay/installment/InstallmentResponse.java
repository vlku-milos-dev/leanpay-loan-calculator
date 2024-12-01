package com.example.leanpay.installment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InstallmentResponse(
        int monthNumber,
        BigDecimal paymentAmount,
        BigDecimal principalAmount,
        BigDecimal interestAmount,
        BigDecimal balanceOwned
) {
}
