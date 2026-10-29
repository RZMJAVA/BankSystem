package com.example.demo.UserOperationServices;

import com.example.demo.UserOperationsDto.SendMoneyRequest;
import com.example.demo.dto.TransferDto;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.User;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.mappers.TransferMapper;
import com.example.demo.services.TransferService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferOperationService {

    private final UserService userService;
    private final TransferService transferService;
    private final TransferMapper transferMapper;

    @Transactional
    public void sendMoney(SendMoneyRequest request, Authentication authentication) {      //  Метод отправки денег
        String username = authentication.getName();
        User sender = userService.findByUsername(username);
        User receiver = userService.findByUsername(request.getReceiverUsername());

        if(transferService.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            throw new RuntimeException("This transfer already exists");
        }
        if(sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Balance is too low");
        }
        if(sender.getId() == receiver.getId()) {
            throw new RuntimeException("Sender is receiver");
        }
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));
        userService.updateUser(sender);
        userService.updateUser(receiver);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setReceiver(receiver);
        transfer.setAmount(request.getAmount());
        transfer.setMessage(request.getMessage());
        transfer.setCreatedAt(LocalDateTime.now());
        transfer.setIdempotencyKey(request.getIdempotencyKey());
        transferService.create(transfer);
    }

    public Page<TransferDto> getSentTransfers(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Long userId = user.getId();
        Page<Transfer> transfers = transferService.findBySenderId(userId, pageable);
        return transferMapper.listToDto(transfers);
    }

    public Page<TransferDto> getReceivedTransfers(Authentication authentication, Pageable pageable) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Long userId = user.getId();
        Page<Transfer> transfers = transferService.findByReceiverId(userId, pageable);
        return transferMapper.listToDto(transfers);
    }

}
