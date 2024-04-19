package org.exercise.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

    @Column(name = "transaction_number", nullable = false)
    private String transactionNumber = UUID.randomUUID()+String.valueOf(UUID.nameUUIDFromBytes(UUID.fromString("00000000-0000-0000-0000-000000000000").toString().concat(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()).toString()).getBytes()));

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "company_id", nullable = false)
    private int companyId;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "unit_price", nullable = false)
    private String unitPrice;

    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @Column(name = "tax_rate", nullable = false)
    private String taxRate;

    @Column(name = "transaction_date", nullable = false)
    private String transactionDate = String.valueOf(new Date());

    @Column(name = "refence_text", nullable = true, length = 1000)
    private String referenceText;

    public String upadateTotalPrice() {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = null;
        if (unitPrice != null && quantity != null) {
            double price = Double.parseDouble(this.unitPrice);
            double quantity = Double.parseDouble(this.quantity);
            this.totalPrice = String.valueOf(price * quantity);
            formattedNumber = df.format(Double.parseDouble(this.totalPrice));
            this.totalPrice = formattedNumber;
        }
        return formattedNumber;
    }
}
