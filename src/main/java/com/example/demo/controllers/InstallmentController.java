package com.example.demo.controllers;

import com.example.demo.UserOperationServices.InstallmentOperationService;
import com.example.demo.UserOperationsDto.PayInstallmentRequest;
import com.example.demo.UserOperationsDto.TakeInstallmentRequest;
import com.example.demo.dto.InstallmentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/installment")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentOperationService installmentOperationService;

    @PostMapping("/take")
    public String takeInstallment(@Valid @RequestBody TakeInstallmentRequest request, Authentication authentication) {
        installmentOperationService.takeInstallment(request, authentication);
        return "Installment successful";
    }

    @PostMapping("/pay")
    public String payInstallment(@RequestBody PayInstallmentRequest request, Authentication authentication) {
        installmentOperationService.payInstallment(request, authentication);
        return "Paying is successful";
    }

    @GetMapping("/info")
    public List<InstallmentDto> installmentDtos(Authentication authentication) {
        return installmentOperationService.readInstallments(authentication);
    }

}
