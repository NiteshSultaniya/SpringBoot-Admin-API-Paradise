package com.example.SpringBlogAdmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment_transactions")
//@JsonIgnoreProperties({"razorpaySignature"})

public class PaymentTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderId;

    @Column(unique = true)
    private String razorpayPaymentId;

    private String razorpayOrderId;

    @Column(columnDefinition = "TEXT")
    private String razorpaySignature;

    private Long amount;

    @Column(length = 50)
    private String status;

    @Column(length = 100)
    private String paymentMethod;

    @Column(length = 100)
    private String errorCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
