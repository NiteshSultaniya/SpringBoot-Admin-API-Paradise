package com.example.SpringBlogAdmin.controller;

import com.example.SpringBlogAdmin.entity.ProductEntity;
import com.example.SpringBlogAdmin.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all-product")
    public ResponseEntity<?> allProduct(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.allProduct(page,size);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/get-category-data")
    public ResponseEntity<?> getCategoryData() {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.getCategoryData();
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/category-wise-product")
    public ResponseEntity<?> getCategoryWiseProduct() {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.getCategoryWiseProduct();
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/find-product-by-id/{id}")
    public ResponseEntity<?> productById(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.productById(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @PostMapping("/product-add-process")
    public ResponseEntity<?> productProcess(@ModelAttribute ProductEntity productEntity, @RequestParam(value="file", required = false) MultipartFile file) {
//        System.out.println(productEntity.getId());
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.productProcess(productEntity, file);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
//            return ResponseEntity.ok("suces");
    }

    @GetMapping("/product-status-update/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.statusUpdate(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/product-delete/{id}")
    public ResponseEntity<?> productDelete(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = productService.productDelete(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
}
