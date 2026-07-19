package com.example.SpringBlogAdmin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment_transactions")
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



}
