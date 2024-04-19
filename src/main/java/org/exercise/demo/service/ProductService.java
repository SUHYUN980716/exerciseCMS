package org.exercise.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.exercise.demo.entity.ProductEntity;
import org.exercise.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ProductEntity save(ProductEntity product) throws Exception {
        try{

            String afterTaxPrice = product.updateAfterTaxPrice();
            product.setAfterTaxPrice(afterTaxPrice);
            return productRepository.save(product);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Page<ProductEntity> findAll(String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("productId").descending());
        Page<ProductEntity> pageing = productRepository.findByNameContainingIgnoreCase(keyword,pageRequest);
        return pageing;
    }

    public Page<ProductEntity> findAllForCom(int costomerId, String keyword, int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("productId").descending());
        Page<ProductEntity> pageing = productRepository.findByCostomerIdAndNameContainingIgnoreCase(costomerId,keyword,pageRequest);
        return pageing;
    }

//    public void delete(Long id) {
//        productRepository.deleteById(id);
//    }

}
