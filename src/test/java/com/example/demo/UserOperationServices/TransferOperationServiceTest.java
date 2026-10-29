package com.example.demo.UserOperationServices;

import com.example.demo.UserOperationsDto.SendMoneyRequest;
import com.example.demo.entities.Transfer;
import com.example.demo.entities.User;
import com.example.demo.mappers.TransferMapper;
import com.example.demo.services.TransferService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferOperationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransferService transferService;

    @Mock
    private TransferMapper transferMapper;

    @InjectMocks
    private TransferOperationService transferOperationService;

    @Test
    void sendMoney_shouldTransferMoneySuccessfully() {
        User sender = new User();
        sender.setId(1L);
        sender.setUsername("Alina Bodanina");
        sender.setBalance(new BigDecimal(10000));

        User receiver = new User();
        receiver.setId(2L);
        receiver.setUsername("Rauan Zhumanov");
        receiver.setBalance(new BigDecimal(10000));

        BigDecimal amount = new BigDecimal("5000");
        SendMoneyRequest request = new SendMoneyRequest();
        request.setAmount(amount);
        request.setReceiverUsername("Rauan Zhumanov");
        request.setIdempotencyKey("test_key_1");

        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("Alina Bodanina");

        when(userService.findByUsername("Alina Bodanina")).thenReturn(sender);

        when(userService.findByUsername("Rauan Zhumanov")).thenReturn(receiver);

        when(transferService.findByIdempotencyKey("test_key_1")).thenReturn(Optional.empty());

        transferOperationService.sendMoney(request, authentication);

        assertEquals(new BigDecimal("5000"), sender.getBalance());
        assertEquals(new BigDecimal("15000"), receiver.getBalance());

        verify(userService).updateUser(sender);
        verify(userService).updateUser(receiver);
        verify(transferService).create(any(Transfer.class));
    }


}
