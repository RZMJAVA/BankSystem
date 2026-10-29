package com.example.demo.UserOperationsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PayInstallmentRequest {

    private Long installmentId;
    private BigDecimal totalAmount;
    private Integer months;

}
