package com.example.leanpay.loan;

import com.example.leanpay.installment.InstallmentResponse;
import com.example.leanpay.installment.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final InstallmentService installmentService;

    @Transactional
    public LoanResponse createLoan(LoanRequest request) {
        Loan loan = loanMapper.requestToEntity(request);
        loan = loanRepository.save(loan);
        List<InstallmentResponse> installments = installmentService.createLoanInstallments(loan);
        return new LoanResponse(installments);
    }

}
