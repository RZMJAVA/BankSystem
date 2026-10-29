package com.example.demo.controllers;

import com.example.demo.UserOperationServices.TransferOperationService;
import com.example.demo.UserOperationsDto.SendMoneyRequest;
import com.example.demo.dto.TransferDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferOperationService transferOperationService;

    @PostMapping("/send")
    public String sendMoney(@Valid @RequestBody SendMoneyRequest request, Authentication authentication) {
        transferOperationService.sendMoney(request, authentication);
        return "Transfer successful";
    }

    @GetMapping("/senthistory")
    public Page<TransferDto> sentHistory(Authentication authentication, Pageable pageable) {
        return transferOperationService.getSentTransfers(authentication, pageable);
    }

    @GetMapping("/receivedhistory")
    public Page<TransferDto> receivedHistory(Authentication authentication, Pageable pageable) {
        return transferOperationService.getReceivedTransfers(authentication, pageable);
    }

}
