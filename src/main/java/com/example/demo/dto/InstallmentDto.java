package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class InstallmentDto {

    private BigDecimal totalAmount;

    private BigDecimal remainingAmount;

    private BigDecimal monthlyPayment;

    private Integer months;

    private Integer paidMonths;

}
