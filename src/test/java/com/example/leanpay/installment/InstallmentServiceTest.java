package com.example.leanpay.installment;

import com.example.leanpay.loan.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class InstallmentServiceTest {

    @InjectMocks
    private InstallmentService installmentService;

    @Mock
    private InstallmentRepository installmentRepository;

    @Mock
    private InstallmentMapper installmentMapper;


    @BeforeEach
    void setUp() {
        openMocks(this); // Initializes the mocks and injects them into installmentService
    }

    @Test
    void createLoanInstallments_ShouldCreateInstallmentPlan() {
        // Arrange
        Loan loan = new Loan(1L, BigDecimal.valueOf(10000), BigDecimal.valueOf(5), 10);

        List<Installment> mockInstallments = List.of(
                Installment.builder().monthNumber(1).paymentAmount(BigDecimal.valueOf(102.31)).build(),
                Installment.builder().monthNumber(2).paymentAmount(BigDecimal.valueOf(102.31)).build()
        );

        List<InstallmentResponse> mockResponses = List.of(
                InstallmentResponse.builder().monthNumber(1).paymentAmount(BigDecimal.valueOf(102.31)).build(),
                InstallmentResponse.builder().monthNumber(2).paymentAmount(BigDecimal.valueOf(102.31)).build()
        );

        when(installmentRepository.saveAll(anyList())).thenReturn(mockInstallments);
        when(installmentMapper.entityToResponse(any(Installment.class)))
                .thenReturn(mockResponses.get(0), mockResponses.get(1));
        when(installmentMapper.entitiesToResponses(anyList()))
                .thenReturn(mockResponses);
        
        // Act
        List<InstallmentResponse> responses = installmentService.createLoanInstallments(loan);

        // Assert
        assertEquals(2, responses.size());
        assertEquals(BigDecimal.valueOf(102.31), responses.get(0).paymentAmount());
        verify(installmentRepository, times(1)).saveAll(anyList());
        verify(installmentMapper, times(1)).entitiesToResponses(mockInstallments);
    }
}