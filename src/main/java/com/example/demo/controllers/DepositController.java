package com.example.demo.controllers;

import com.example.demo.UserOperationServices.DepositOperationService;
import com.example.demo.UserOperationsDto.DepositRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final DepositOperationService depositOperationService;

    @PostMapping("/to")
    public String toDeposit(@Valid @RequestBody DepositRequest request, Authentication authentication) {
        depositOperationService.toDeposit(request, authentication);
        return "Deposit successful";
    }

    @PostMapping("/from")
    public String fromDeposit(@Valid @RequestBody DepositRequest request, Authentication authentication) {
        depositOperationService.fromDeposit(request, authentication);
        return "Deposit withdrawal successful";
    }

}
