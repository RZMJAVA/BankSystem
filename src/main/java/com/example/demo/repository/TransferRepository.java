package com.example.demo.repository;


import com.example.demo.entities.Installment;
import com.example.demo.entities.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Page<Transfer> findBySenderId(Long userId, Pageable pageable);
    Page<Transfer> findByReceiverId(Long userId, Pageable pageable);
    Optional<Transfer> findByIdempotencyKey(String idempotencyKey);

}
