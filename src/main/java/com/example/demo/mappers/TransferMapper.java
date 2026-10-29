package com.example.demo.mappers;

import com.example.demo.dto.TransferDto;
import com.example.demo.entities.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferMapper {

    public TransferDto toDto(Transfer transfer) {
        TransferDto transferDto = new TransferDto();
        transferDto.setSenderName(transfer.getSender().getUsername());
        transferDto.setReceiverName(transfer.getReceiver().getUsername());
        transferDto.setAmount(transfer.getAmount());
        transferDto.setMessage(transfer.getMessage());
        transferDto.setCreatedAt(transfer.getCreatedAt());
        return transferDto;
    }

    public Page<TransferDto> listToDto(Page<Transfer> transfers) {
        return transfers.map(this::toDto);
    }




}
