package com.example.leanpay.loan;

import com.example.leanpay.installment.InstallmentResponse;

import java.util.List;


public record LoanResponse(
        List<InstallmentResponse> installments
) {
}
