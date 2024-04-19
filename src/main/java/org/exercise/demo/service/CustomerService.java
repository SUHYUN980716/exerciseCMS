package org.exercise.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.exercise.demo.entity.CustomerEntity;
import org.exercise.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity save(CustomerEntity customerEntity) {

        CustomerEntity newCustomer = new CustomerEntity(
                customerEntity.getRole(),
                customerEntity.getUsername(),
                passwordEncoder.encode(customerEntity.getPassword()),
                customerEntity.getEmail(),
                customerEntity.getTaxNumber()
        );
        return customerRepository.save(newCustomer);
    }


    public Object findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Transactional
    public void updateRecentLogin(String username) {
        try {
            Optional<CustomerEntity> optionalEntity = customerRepository.findByUsername(username);
            if (optionalEntity.isPresent()) {
                CustomerEntity entity = optionalEntity.get();
                entity.setRecentLogin(String.valueOf(new Date()));
                entityManager.merge(entity);
            } else {
                System.out.println("Cannot find user with username: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
