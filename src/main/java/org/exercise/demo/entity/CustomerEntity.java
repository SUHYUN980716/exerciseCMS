package org.exercise.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Customer")
public final class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "username", nullable = false)
    @Setter(AccessLevel.NONE)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "tax_number", nullable = true)
    private String taxNumber;

    @Column(name = "registered_date", nullable = false)
    private String registeredDate = String.valueOf(new Date());

    @Column(name = "modified_date", nullable = true)
    private String modifiedDate = "haven't modified yet";

    @Column(name = "recent_login", nullable = true)
    private String recentLogin;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "refence_text", nullable = true, length = 1000)
    private String referenceText;

    public CustomerEntity(String role, String username, String encode, String email, String taxNumber) {
        this.role = role;
        this.username = username;
        this.email = email;
        this.password = encode;
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() {
        return this.getIdentificationNumber();
    }

    public String getIdentificationNumber() {
        if (this.taxNumber != null) {
            return this.taxNumber;
        } else {
            return null;
        }

    }

    @Column(name = "role", nullable = true, length = 1000)
    private String role;
}
