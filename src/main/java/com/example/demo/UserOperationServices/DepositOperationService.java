package com.example.demo.UserOperationServices;

import com.example.demo.UserOperationsDto.DepositRequest;
import com.example.demo.entities.DailyDepositBalance;
import com.example.demo.entities.User;
import com.example.demo.services.DDBalanceService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositOperationService {

    private final UserService userService;
    private final DDBalanceService ddBalanceService;

    @Transactional
    public void toDeposit(DepositRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if(user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Balance is too low");
        }
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        user.setBalance(user.getBalance().subtract(request.getAmount()));
        user.setDepositBalance(user.getDepositBalance().add(request.getAmount()));
    }

    @Transactional
    public void fromDeposit(DepositRequest request, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if(user.getDepositBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Balance is too low");
        }
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        user.setDepositBalance(user.getDepositBalance().subtract(request.getAmount()));
        user.setBalance(user.getBalance().add(request.getAmount()));
    }


    @Transactional
    public void addDdBalanceForUser(User user) {
            DailyDepositBalance dailyDepositBalance = new DailyDepositBalance();
            dailyDepositBalance.setUser(user);
            dailyDepositBalance.setAmount(user.getDepositBalance());
            dailyDepositBalance.setDate(LocalDateTime.now());
            ddBalanceService.create(dailyDepositBalance);
    }

    @Transactional
    public void addMonthPercent(User user) {
            BigDecimal percent = this.calculateMonthPercent(user);
            user.setDepositBalance(user.getDepositBalance().add(percent));
            userService.updateUser(user);
            ddBalanceService.deleteByUserId(user.getId());
    }

    public BigDecimal calculateMonthPercent(User user) {
        List<DailyDepositBalance> ddBalances = ddBalanceService.findByUserId(user.getId());
        BigDecimal sum = BigDecimal.ZERO;

        for (DailyDepositBalance ddBalance : ddBalances) {
            sum.add(ddBalance.getAmount());
        }

        BigDecimal average = sum.divide(BigDecimal.valueOf(ddBalances.size()), 2, RoundingMode.HALF_UP);
        return average.multiply(new BigDecimal("0.01"));
    }


}
