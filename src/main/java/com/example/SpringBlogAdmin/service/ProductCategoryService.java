package com.example.SpringBlogAdmin.service;


import com.example.SpringBlogAdmin.entity.ProductCategoryEntity;
import com.example.SpringBlogAdmin.repo.ProductCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepo productCategoryRepo;
    private final Supplier<Long> idGenerator;

    public ProductCategoryService(ProductCategoryRepo productCategoryRepo, Supplier<Long> idGenerator) {
        this.productCategoryRepo = productCategoryRepo;
        this.idGenerator = idGenerator;
    }

    public Map<String, Object> allCtegory() {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            List<ProductCategoryEntity> categorydata = productCategoryRepo.findAll();
            if (categorydata.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("data", categorydata);
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }


    public Map<String, Object> categoryById(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            ProductCategoryEntity obj = productCategoryRepo.findById(id).orElseThrow();
            mapdata.put("status", 200);
            mapdata.put("msg", "Data Fetched SuccessFully");
            mapdata.put("data", obj);
            return mapdata;
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> categoryProcess(ProductCategoryEntity category) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            System.out.println(category.getId());
            if (category.getId() > 0 && category.getId() != null) {
                Optional<ProductCategoryEntity> existingCatOpt = productCategoryRepo.findById(category.getId());
                if (existingCatOpt.isEmpty()) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Category not found");
                    return mapdata;
                }
                ProductCategoryEntity existingCat = existingCatOpt.get();
                if (!existingCat.getName().equals(category.getName())) {
                    Boolean emailAlreadyExists = productCategoryRepo.findByName(category.getName());
                    if (emailAlreadyExists) {
                        mapdata.put("status", 201);
                        mapdata.put("msg", "Category already exists");
                        return mapdata;
                    }
                }
                productCategoryRepo.save(category);
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Updated Successfully");
                return mapdata;
            } else {
                Boolean catExists = productCategoryRepo.findByName(category.getName());
                if (catExists) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Category already exists");
                    return mapdata;
                } else {
                    category.setId(idGenerator.get());
                    System.out.println(category.getName());
                    System.out.println(category.getSlug());

                    category.setName(category.getName());
                    category.setSlug(category.getSlug());
                    productCategoryRepo.save(category);
                    mapdata.put("status", 200);
                    mapdata.put("msg", "Category added Succesffulyy");
                    return mapdata;
                }
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> statusUpdate(Long id) {

        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            ProductCategoryEntity cat = productCategoryRepo.findById(id).orElseThrow();
            Integer currentStatus = cat.getStatus();
            Integer newStatus = (currentStatus == 1) ? 0 : 1;
            productCategoryRepo.updateStatusById(newStatus, id);
            mapdata.put("status", 200);
            mapdata.put("msg", "Status Updated Succesffulyy");
            return mapdata;
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }
}
