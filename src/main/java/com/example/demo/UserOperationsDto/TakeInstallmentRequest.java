package com.example.demo.UserOperationsDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TakeInstallmentRequest {

    @NotNull
    @Positive
    private BigDecimal totalAmount;

    @NotNull
    @Positive
    private Integer months;
}
