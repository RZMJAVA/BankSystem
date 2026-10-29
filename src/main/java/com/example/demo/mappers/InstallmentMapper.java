package com.example.demo.mappers;

import com.example.demo.dto.InstallmentDto;
import com.example.demo.entities.Installment;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstallmentMapper {

    public InstallmentDto toDto(Installment installment) {
        InstallmentDto installmentDto = new InstallmentDto();
        installmentDto.setTotalAmount(installment.getTotalAmount());
        installmentDto.setMonths(installment.getMonths());
        installmentDto.setMonthlyPayment(installment.getMonthlyPayment());
        installmentDto.setPaidMonths(installment.getPaidMonths());
        installmentDto.setRemainingAmount(installment.getRemainingAmount());
        return installmentDto;
    }

    public List<InstallmentDto> toDtoList(List<Installment> installments) {
        List<InstallmentDto> installmentDtos = new ArrayList<>();
        for(Installment installment : installments) {
            installmentDtos.add(this.toDto(installment));
        }
        return installmentDtos;
    }

}
