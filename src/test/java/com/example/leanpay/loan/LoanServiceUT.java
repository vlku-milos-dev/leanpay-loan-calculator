package com.example.leanpay.loan;

import com.example.leanpay.installment.Installment;
import com.example.leanpay.installment.InstallmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class LoanServiceUT {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private InstallmentService installmentService;

    @BeforeEach
    void setUp() {
        openMocks(this); // Initializes the mocks and injects them into installmentService
    }


    @Test
    void createLoan_ShouldCreateLoanAndReturnLoanResponse() {

        // Arrange
        List<Installment> installments = List.of(
                Installment.builder().monthNumber(1).paymentAmount(BigDecimal.valueOf(102.31)).build(),
                Installment.builder().monthNumber(2).paymentAmount(BigDecimal.valueOf(102.31)).build());
        Loan loan = Loan.builder()
                .amount(BigDecimal.valueOf(1000.00))
                .annualInterestPercentage(BigDecimal.valueOf(5.00))
                .numberOfMonths(10)
                .installments(installments)
                .build();


        when(loanRepository.save(loan)).thenReturn(loan);
        when(installmentService.calculateInstallments(loan.getAmount(), loan.getAnnualInterestPercentage(),
                loan.getNumberOfMonths())).thenReturn(installments);

        // Act
        Loan loanResponse =
                loanService.createLoan(loan.getAmount(), loan.getAnnualInterestPercentage(), 10);

        // Assert
        verify(loanRepository).save(loan);
        assertNotNull(loanResponse);
        assertEquals(2, loanResponse.getInstallments().size());
    }
}