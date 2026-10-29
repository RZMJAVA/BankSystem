package com.example.demo.repository;

import com.example.demo.entities.DailyDepositBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DDBRepository extends JpaRepository<DailyDepositBalance, Long> {

    List<DailyDepositBalance> findByUserId(Long userId);
    void deleteByUserId(Long userId);

}
