package com.example.demo.services;

import com.example.demo.entities.Installment;
import com.example.demo.entities.User;
import com.example.demo.repository.InstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentService {

    private final InstallmentRepository installmentRepository;

    public Installment create(Installment installment) {
        return installmentRepository.save(installment);
    }

    public Installment findById(Long id) {
        return installmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public List<Installment> findByUser(Long userId) {
        return installmentRepository.findByUserId(userId);
    }

    public void delete(Long id) {
        installmentRepository.delete(this.findById(id));
    }

    public Installment updateInstallment(Installment installment) {
        return installmentRepository.save(installment);
    }

}
