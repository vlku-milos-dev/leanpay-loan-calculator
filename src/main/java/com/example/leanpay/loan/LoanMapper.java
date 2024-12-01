package com.example.leanpay.loan;

import com.example.leanpay.installment.InstallmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanMapper {

    private final InstallmentMapper installmentMapper;

    public LoanResponse entityToResponse(Loan loan) {
        return new LoanResponse(installmentMapper.entitiesToResponses(loan.getInstallments()));
    }
}
