package com.example.demo.UserOperationServices;

import com.example.demo.UserOperationsDto.PayInstallmentRequest;
import com.example.demo.UserOperationsDto.TakeInstallmentRequest;
import com.example.demo.dto.InstallmentDto;
import com.example.demo.entities.Installment;
import com.example.demo.entities.User;
import com.example.demo.mappers.InstallmentMapper;
import com.example.demo.services.InstallmentService;
import com.example.demo.services.TransferService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentOperationService {

    private final UserService userService;
    private final InstallmentService installmentService;
    private final InstallmentMapper installmentMapper;

    @Transactional    //Метод ежемесячного платежа по рассрочке
    public void payInstallment(PayInstallmentRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Installment installment = installmentService.findById(request.getInstallmentId());

        if(user.getBalance().compareTo(installment.getMonthlyPayment()) < 0) {
            throw new RuntimeException("Balance is too low");
        }

        if(installment.getRemainingAmount().compareTo(installment.getTotalAmount()) <= 0) {
            throw new RuntimeException("Installment is already paid");
        }

        user.setBalance(user.getBalance().subtract(installment.getMonthlyPayment()));
        installment.setRemainingAmount(installment.getRemainingAmount().subtract(installment.getMonthlyPayment()));
        installment.setPaidMonths(installment.getPaidMonths() + 1);
        userService.updateUser(user);
        installmentService.updateInstallment(installment);
    }

    @Transactional    //Метод взятия рассрочки
    public void takeInstallment(TakeInstallmentRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        Installment installment = new Installment();
        installment.setUser(user);
        installment.setTotalAmount(request.getTotalAmount());
        installment.setMonths(request.getMonths());
        installment.setRemainingAmount(installment.getTotalAmount());
        installment.setMonthlyPayment(installment.getTotalAmount().divide(
                BigDecimal.valueOf(installment.getMonths()),
                2,
                RoundingMode.HALF_UP));
        installment.setPaidMonths(0);

        user.setBalance(user.getBalance().add(installment.getTotalAmount()));

        installmentService.create(installment);
    }

    public List<InstallmentDto> readInstallments(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return installmentMapper.toDtoList(installmentService.findByUser(user.getId()));
    }

}
