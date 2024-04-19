package org.exercise.demo.controller;

import org.exercise.demo.entity.CustomerEntity;
import org.exercise.demo.entity.ProductEntity;
import org.exercise.demo.entity.TransactionEntity;
import org.exercise.demo.service.CustomerService;
import org.exercise.demo.service.ProductService;
import org.exercise.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    public CustomerService customerService;

    @Autowired
    public ProductService productService;

    @Autowired
    public TransactionService transactionService;

    public Controller(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/saveProduct")
    public void saveProduct(@RequestBody ProductEntity productEntity) throws Exception {
        DecimalFormat df = new DecimalFormat("#.##");
        productService.save(productEntity);
    }

    @PostMapping("/listProduct")
    public ResponseEntity<Page<ProductEntity>> listProduct(@RequestParam int page, @RequestParam String keyword){
        return ResponseEntity.ok(productService.findAll(keyword, page));
    }

    @PostMapping("/listProductForCom")
    public ResponseEntity<Page<ProductEntity>> listProductForCom(@RequestParam int costomerId,@RequestParam int page, @RequestParam String keyword){
        return ResponseEntity.ok(productService.findAllForCom(costomerId,keyword, page));
    }

    @PostMapping("/purchase")
    public void purchaseProduct(@RequestBody TransactionEntity transaction) throws Exception {
        transactionService.save(transaction);
    }

    @PostMapping("/listTransaction")
    public ResponseEntity<Page<TransactionEntity>> listPurchase(@RequestParam int customerId, @RequestParam int page, @RequestParam String keyword){
        return ResponseEntity.ok(transactionService.findAll(customerId,keyword, page));
    }

    @PostMapping("/listTransactionForCom")
    public ResponseEntity<Page<TransactionEntity>> listTransactionForCom(@RequestParam int companyId, @RequestParam int page, @RequestParam String keyword){
        return ResponseEntity.ok(transactionService.findAllForCom(companyId,keyword, page));
    }

    @PostMapping("/register")
    public CustomerEntity createUser(@RequestBody CustomerEntity customerEntity) throws Exception {

        return customerService.save(customerEntity);
    }

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerEntity customerEntity) {
            customerService.updateRecentLogin(customerEntity.getUsername());
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(customerEntity.getUsername(),customerEntity.getPassword());
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);


            if(authenticationResponse.isAuthenticated()){
                return ResponseEntity.ok(customerService.findByUsername(customerEntity.getUsername()));
            }else{
                return ResponseEntity.ok("Invalid username or password");
            }

    }

    @PostMapping("/logout")
    public void logout() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            SecurityContextHolder.getContext().setAuthentication(null);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



}
