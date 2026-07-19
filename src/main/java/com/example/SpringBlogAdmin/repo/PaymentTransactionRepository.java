package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity,Long> {
}
