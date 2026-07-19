package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.PaymentOrderEntity;
import com.example.SpringBlogAdmin.entity.PaymentTransactionEntity;
import com.example.SpringBlogAdmin.repo.PaymentOrderRepository;
import com.example.SpringBlogAdmin.repo.PaymentTransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.*;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    public PaymentTransactionRepository paymentTransactionRepository;
    public PaymentOrderRepository paymentOrderRepository;
    @Value("${RAZORPAY_KEY_ID}")
    String KEY_ID;
    @Value("${RAZORPAY_KEY_SECRET}")
    String KEY_SECRET;

    public PaymentService(PaymentOrderRepository paymentOrderRepository, PaymentTransactionRepository paymentTransactionRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Transactional
    public Map<String, Object> createOrder(PaymentOrderEntity paymentOrderEntity) {

        Map<String, Object> obj = new HashMap<>();
        try {
            RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", paymentOrderEntity.getAmount() * 100);
            orderRequest.put("currency", "INR");
            Order order = razorpay.orders.create(orderRequest);

            String productId = "PRODUCT_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Integer amount = order.get("amount");
            paymentOrderEntity.setAmount(amount.longValue());
            paymentOrderEntity.setRazorpayOrderId(order.get("id"));
            paymentOrderEntity.setProductId(productId);
            paymentOrderEntity.setStatus("CREATED");


            paymentOrderRepository.save(paymentOrderEntity);


            JSONObject orderJson = order.toJson();
            obj.put("status", 200);
            obj.put("data", orderJson.toMap());
            obj.put("msg", "data fetched successfully");
            return obj;

        } catch (Exception e) {
            obj.put("status", 201);
            obj.put("msg", e);
            return obj;

        }
    }

    public Map<String, Object> verifyPayment(PaymentTransactionEntity paymentTransactionEntity) {
        Map<String, Object> obj = new HashMap<>();


//        try {
//            RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);
//            Payment paymentDetails = client.payments.fetch(paymentTransactionEntity.getRazorpayPaymentId());
//            System.out.println(paymentDetails.toString());
//            obj.put("status", 200);
//            obj.put("data", new ObjectMapper().readValue(paymentDetails.toString(), Map.class));
//            obj.put("msg", "Payment Successful");
//            return obj;
//        } catch (RuntimeException | RazorpayException e) {
//            throw new RuntimeException(e);
//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }


        try {
            String generatedSignature = paymentTransactionEntity.getRazorpayOrderId() + "|" + paymentTransactionEntity.getRazorpayPaymentId();
            boolean isValid = Utils.verifySignature(generatedSignature, paymentTransactionEntity.getRazorpaySignature(), KEY_SECRET);

            if (!isValid) {
                obj.put("status", 200);
                obj.put("msg", "Payment Failed");
                return obj;
            }
            RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);
            Payment paymentDetails = client.payments.fetch(paymentTransactionEntity.getRazorpayPaymentId());
            paymentTransactionEntity.setAmount(Long.valueOf((Integer)paymentDetails.get("amount")));
//            System.out.println((String)paymentDetails.get("error_code"));
//            System.out.println("hello");
//            Object errorCode = paymentDetails.get("error_code");
//            paymentTransactionEntity.setErrorCode(
//                    errorCode == JSONObject.NULL ? null : errorCode.toString()
//            );

            paymentTransactionEntity.setErrorCode(getStringOrNull(paymentDetails,"error_code"));
            paymentTransactionEntity.setOrderId(getStringOrNull(paymentDetails,"order_id"));
            paymentTransactionEntity.setPaymentMethod(getStringOrNull(paymentDetails,"method"));
            paymentTransactionEntity.setRazorpayOrderId(paymentTransactionEntity.getRazorpayOrderId());
            paymentTransactionEntity.setRazorpayPaymentId(paymentTransactionEntity.getRazorpayPaymentId());
            paymentTransactionEntity.setRazorpaySignature(paymentTransactionEntity.getRazorpaySignature());
            paymentTransactionEntity.setStatus(getStringOrNull(paymentDetails,"status"));
            paymentTransactionRepository.save(paymentTransactionEntity);

            obj.put("status", 200);
            obj.put("msg", "Payment Successful");
            return obj;
        } catch (Exception e) {
            obj.put("status", 201);
            obj.put("msg", e.getMessage());
            return obj;

        }
    }

    public Map<String, Object> paymentFailed(PaymentTransactionEntity paymentTransactionEntity) {
        Map<String, Object> obj = new HashMap<>();
        try {
            RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);
            Payment paymentDetails = client.payments.fetch(paymentTransactionEntity.getRazorpayPaymentId());
            if(paymentDetails !=null && "failed".equals(getStringOrNull(paymentDetails,"status"))){
            paymentTransactionEntity.setAmount(Long.valueOf((Integer)paymentDetails.get("amount")));
            paymentTransactionEntity.setErrorCode(getStringOrNull(paymentDetails,"error_code"));
            paymentTransactionEntity.setOrderId(getStringOrNull(paymentDetails,"order_id"));
            paymentTransactionEntity.setPaymentMethod(getStringOrNull(paymentDetails,"method"));
            paymentTransactionEntity.setRazorpayOrderId(null);
            paymentTransactionEntity.setRazorpayPaymentId(paymentTransactionEntity.getRazorpayPaymentId());
            paymentTransactionEntity.setRazorpaySignature(null);
            paymentTransactionEntity.setStatus(getStringOrNull(paymentDetails,"status"));
            paymentTransactionRepository.save(paymentTransactionEntity);
//            obj.put("data", new ObjectMapper().readValue(paymentDetails.toString(), Map.class));
            obj.put("status", 200);
            obj.put("msg", "Payment failed");
            }else {
                obj.put("status", 200);
                obj.put("msg", "Something Went Wrong");
            }
            return obj;
        } catch (Exception e) {
            obj.put("status", 201);
            obj.put("msg", "Invalid Request");
            return obj;

        }
    }

    //Null object exception
    private String getStringOrNull(Payment payment, String key) {
        Object value = payment.get(key);
        return value == JSONObject.NULL ? null : value.toString();
    }
}
