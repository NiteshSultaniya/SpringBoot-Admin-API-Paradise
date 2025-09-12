package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.ProductCategoryEntity;
import com.example.SpringBlogAdmin.service.ProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product/category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    Map<String, Object> mapdata = new LinkedHashMap<>();

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/all-category")
    public ResponseEntity<?> allCtegory() {
        try {
            Map<String, Object> obj = productCategoryService.allCtegory();
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/find-category-by-id/{id}")
    public ResponseEntity<?> categoryById(@PathVariable("id") Long id) {
        try {
            Map<String, Object> obj = productCategoryService.categoryById(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
    @PostMapping("/category-add-process")
    public ResponseEntity<?> categoryProcess(@RequestBody ProductCategoryEntity productCategoryEntity) {
        try {
            Map<String, Object> obj = productCategoryService.categoryProcess(productCategoryEntity);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
    @GetMapping("/category-status-update/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable("id") Long id) {
        try {
            Map<String, Object> obj = productCategoryService.statusUpdate(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
}
