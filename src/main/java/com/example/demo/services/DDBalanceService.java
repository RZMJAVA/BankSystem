package com.example.demo.services;

import com.example.demo.entities.DailyDepositBalance;
import com.example.demo.repository.DDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DDBalanceService {

    private final DDBRepository ddbRepository;

    public DailyDepositBalance create(DailyDepositBalance ddBalance) {
        return ddbRepository.save(ddBalance);
    }

    public DailyDepositBalance findById(Long id) {
        return ddbRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<DailyDepositBalance> findByUserId(Long userId) {
        return ddbRepository.findByUserId(userId);
    }

    public DailyDepositBalance update(DailyDepositBalance ddBalance) {
        return ddbRepository.save(ddBalance);
    }

    public void delete(DailyDepositBalance ddBalance) {
        ddbRepository.delete(ddBalance);
    }

    public void deleteByUserId(Long userId) {
        ddbRepository.deleteByUserId(userId);
    }

}
