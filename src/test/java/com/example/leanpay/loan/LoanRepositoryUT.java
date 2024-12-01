package com.example.leanpay.loan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanRepositoryUT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    LoanRepository loanRepository;

    @BeforeAll
    static void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    List<Loan> testLoans = List.of(
            Loan.builder()
                    .amount(new BigDecimal("1000.00"))
                    .annualInterestPercentage(new BigDecimal("5.00"))
                    .numberOfMonths(5)
                    .build(),
            Loan.builder()
                    .amount(new BigDecimal("2000.00"))
                    .annualInterestPercentage(new BigDecimal("10.00"))
                    .numberOfMonths(10)
                    .build(),
            Loan.builder()
                    .amount(new BigDecimal("3000.00"))
                    .annualInterestPercentage(new BigDecimal("15.00"))
                    .numberOfMonths(15)
                    .build()
    );

    @BeforeEach
    void setUp(TestInfo testInfo) {

        if (testInfo.getDisplayName().equals("connectionEstablished()")) {
            return;
        }

        loanRepository.saveAll(testLoans);
    }

    @Test
    void shouldFindAllLoans() {
        List<Loan> loans = loanRepository.findAll();

        assertThat(loans).usingRecursiveAssertion().isEqualTo(testLoans)
                .as("Verifying that all loans are saved correctly");
    }
}