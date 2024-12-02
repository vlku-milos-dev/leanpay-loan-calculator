package com.example.leanpay.loan;

import com.example.leanpay.installment.InstallmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanControllerIT {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void createLoan_shouldCreateLoanAndReturnLoanResponse() {
        // Arrange
        int numberOfMonths = 10;
        LoanRequest request = new LoanRequest(BigDecimal.valueOf(1000.00), BigDecimal.valueOf(5.00), numberOfMonths);
        InstallmentResponse expectedInstallment = new InstallmentResponse(
                1,
                BigDecimal.valueOf(102.31),
                BigDecimal.valueOf(98.14),
                BigDecimal.valueOf(4.17),
                BigDecimal.valueOf(901.86));

        // Act
        LoanResponse response = restTemplate.postForObject("/api/v1/loans", request, LoanResponse.class);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.installments().size()).isEqualTo(numberOfMonths);
        assertThat(response.installments().get(0)).isEqualTo(expectedInstallment);
    }

    @Test
    void createLoan_shouldReturnBadRequestWhenLoanRequestParametersAreInvalid() {
        // Arrange
        LoanRequest request = new LoanRequest(
                BigDecimal.valueOf(-1000.00),
                BigDecimal.valueOf(-1.00),
                999);

        // Note: The following code is reachable despite IDE warnings
        // Act
        ResponseEntity<Map<String, ArrayList<String>>> response = restTemplate.exchange(
                "/api/v1/loans",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                });

        // Assert
        assertThat(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)).isTrue();
        Map<String, ArrayList<String>> responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.containsKey("validationErrors")).isTrue();
        assertThat(responseBody.get("validationErrors").size() == 3).isTrue();
    }
}