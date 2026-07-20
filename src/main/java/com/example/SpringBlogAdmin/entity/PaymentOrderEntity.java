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
@Table(name = "payment_orders")
public class PaymentOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String productId;

    @Column(unique = true)
    private String razorpayOrderId;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 10)
    private String currency = "INR";

    private String receipt;

    @Column(length = 50)
    private String status = "CREATED";

    private String email;

    private String name;

    private Long contact;

}
