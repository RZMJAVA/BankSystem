package com.example.demo.scheduler;

import com.example.demo.UserOperationServices.DepositOperationService;
import com.example.demo.entities.DailyDepositBalance;
import com.example.demo.entities.User;
import com.example.demo.services.DDBalanceService;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepositScheduler {

    private final DepositOperationService depositOperationService;
    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void dailyTick() {
        List<User> users = userService.findAll();
        if(LocalDateTime.now().getDayOfMonth() == 1) {
            for(User user : users) {
                depositOperationService.addMonthPercent(user);
            }
        }
        for(User user : users) {
            depositOperationService.addDdBalanceForUser(user);
        }
    }

}
