package com.example.demo.services;

import com.example.demo.entities.Transfer;
import com.example.demo.entities.User;
import com.example.demo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    public Transfer create(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    public Transfer findById(Long id) {
        return transferRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Page<Transfer> findBySenderId(Long senderId, Pageable pageable) {
        return transferRepository.findBySenderId(senderId, pageable);
    }

    public Page<Transfer> findByReceiverId(Long receiverId, Pageable pageable) {
        return transferRepository.findByReceiverId(receiverId, pageable);
    }

    public Optional<Transfer> findByIdempotencyKey(String idempotencyKey) {
        return transferRepository.findByIdempotencyKey(idempotencyKey);
    }

    public Transfer update(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    public void delete(Long transferId) {
        Transfer transfer = this.findById(transferId);
        transferRepository.delete(transfer);
    }

}
