package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity,Long> {
    Optional<PaymentOrderEntity> findByRazorpayOrderId(String orderId);
}
