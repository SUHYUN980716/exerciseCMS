package org.exercise.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.exercise.demo.entity.CustomerEntity;
import org.exercise.demo.entity.ProductEntity;
import org.exercise.demo.entity.TransactionEntity;
import org.exercise.demo.repository.ProductRepository;
import org.exercise.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(TransactionEntity transaction) throws Exception {
        Optional<ProductEntity> optionalEntity = productRepository.findByProductId(transaction.getProductId());
        if (optionalEntity.isPresent()) {
            ProductEntity entity = optionalEntity.get();
            entity.setQuantityAvailable(String.valueOf(Integer.parseInt(entity.getQuantityAvailable()) - Integer.parseInt(transaction.getQuantity())));
            entityManager.merge(entity);
        } else {
            System.out.println("Cannot find product");
        }

        try{
            transaction.upadateTotalPrice();
            transactionRepository.save(transaction);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Page<TransactionEntity> findAll(int customerId, String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("transactionId").descending());
        Page<TransactionEntity> pageing = transactionRepository.findByCustomerIdAndTransactionNumberContainingIgnoreCase(customerId,keyword,pageRequest);
        return pageing;
    }

    public Page<TransactionEntity> findAllForCom(int companyId, String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("transactionId").descending());
        Page<TransactionEntity> pageing = transactionRepository.findByCompanyIdAndTransactionNumberContainingIgnoreCase(companyId,keyword,pageRequest);
        return pageing;
    }
}
