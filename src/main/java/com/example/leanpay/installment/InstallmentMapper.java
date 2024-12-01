package com.example.leanpay.installment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallmentMapper {

    public InstallmentResponse entityToResponse(Installment installment) {
        return new InstallmentResponse(
                installment.getMonthNumber(),
                installment.getPaymentAmount(),
                installment.getPrincipalAmount(),
                installment.getInterestAmount(),
                installment.getBalanceOwned());
    }

    public List<InstallmentResponse> entitiesToResponses(List<Installment> installments) {
        return installments.stream().map(this::entityToResponse).toList();
    }
}
