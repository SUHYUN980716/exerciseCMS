package org.exercise.demo.repository;

import org.exercise.demo.entity.ProductEntity;
import org.exercise.demo.entity.TransactionEntity;
import org.exercise.demo.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Page<TransactionEntity> findByTransactionDateContainingIgnoreCase(String keyword, PageRequest pageRequest);

    Page<TransactionEntity> findByCustomerIdAndTransactionNumberContainingIgnoreCase(int costomerId, String keyword, PageRequest pageRequest);
    Page<TransactionEntity> findByCompanyIdAndTransactionNumberContainingIgnoreCase(int companyId, String keyword, PageRequest pageRequest);
}
