package org.exercise.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "costomer_id", nullable = false)
    private int costomerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "unitPrice", nullable = false)
    private String unitPrice;

    @Column(name = "tax_rate", nullable = false)
    private String taxRate;

    @Column(name = "afterTaxPrice", nullable = false)
    private String afterTaxPrice;

    @Column(name = "quantity_available", nullable = false)
    private String quantityAvailable;

    public ProductEntity(ProductEntity product) {
    }

    public String updateAfterTaxPrice() {
        DecimalFormat df = new DecimalFormat("#.##");

        double afterTaxPriceValue = 0;
        String formattedNumber = null;
        if (unitPrice != null && taxRate != null) {
            double price = Double.parseDouble(unitPrice);
            double rate = Double.parseDouble(taxRate);
            afterTaxPriceValue = price - (price * rate / 100);
            formattedNumber = df.format(afterTaxPriceValue);
        }
        return formattedNumber;
    }
}
