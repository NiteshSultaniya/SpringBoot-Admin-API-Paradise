package com.example.SpringBlogAdmin.controller;

import com.example.SpringBlogAdmin.entity.PaymentOrderEntity;
import com.example.SpringBlogAdmin.entity.PaymentTransactionEntity;
import com.example.SpringBlogAdmin.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;
    public PaymentController(PaymentService paymentService)
    {
        this.paymentService=paymentService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody PaymentOrderEntity orderEntity) {

            Map<String, Object> mapData = new HashMap<>();
        try {
            Map<String, Object> obj =paymentService.createOrder(orderEntity);
            return ResponseEntity.ok(obj);

        } catch (Exception e) {

            return ResponseEntity.ok(mapData);

        }
    }
    @PostMapping("/verify-order")
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentTransactionEntity paymentTransactionEntity) {
        Map<String, Object> mapData = new HashMap<>();
        try {
            Map<String, Object> obj =paymentService.verifyPayment(paymentTransactionEntity);
            return ResponseEntity.ok(obj);

        } catch (Exception e) {

            return ResponseEntity.ok(mapData);

        }
    }
    @PostMapping("/failed")
    public ResponseEntity<?> paymentFailed(@RequestBody PaymentTransactionEntity paymentTransactionEntity)
    {
        Map<String, Object> mapData = new HashMap<>();
        try {
            Map<String, Object> obj =paymentService.paymentFailed(paymentTransactionEntity);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            return ResponseEntity.ok(mapData);
        }
    }

    @GetMapping("/all-order")
    public ResponseEntity<?> order()
    {
        Map<String, Object> mapData = new HashMap<>();
        try {
            Map<String, Object> obj =paymentService.order();
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            return ResponseEntity.ok(mapData);
        }
    }

}
