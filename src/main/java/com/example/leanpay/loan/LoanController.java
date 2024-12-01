package com.example.leanpay.loan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans")
class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    @Operation(
            summary = "Create Loan",
            description = "This endpoint allows users to create a new loan by providing loan details.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest request) {

        Loan loan = loanService.createLoan(
                request.amount(),
                request.annualInterestPercentage(),
                request.numberOfMonths()
        );

        return ResponseEntity.ok(loanMapper.entityToResponse(loan));
    }
}
