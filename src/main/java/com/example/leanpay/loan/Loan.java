package com.example.leanpay.loan;

import com.example.leanpay.installment.Installment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private BigDecimal amount;
    private BigDecimal annualInterestPercentage;
    private int numberOfMonths;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Installment> installments;
}
