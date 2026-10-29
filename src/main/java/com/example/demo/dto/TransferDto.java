package com.example.demo.dto;


import com.example.demo.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransferDto {

    private String senderName;

    private String receiverName;

    private BigDecimal amount;

    private String message;

    private LocalDateTime createdAt;

}
