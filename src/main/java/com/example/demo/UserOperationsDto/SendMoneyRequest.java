package com.example.demo.UserOperationsDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SendMoneyRequest {

    @NotBlank
    private String receiverUsername;

    @NotNull
    @Positive
    private BigDecimal amount;

    private String message;

    private String idempotencyKey;

}
