package org.exercise.demo.repository;

import org.exercise.demo.entity.CustomerEntity;
import org.exercise.demo.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByNameContainingIgnoreCase(String keyword, PageRequest pageRequest);

    Page<ProductEntity> findByCostomerIdAndNameContainingIgnoreCase(int costomerId, String keyword, PageRequest pageRequest);

    Optional<ProductEntity> findByProductId(int productId);
}
