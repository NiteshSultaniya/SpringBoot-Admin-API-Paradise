package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.ProductEntity;
import com.example.SpringBlogAdmin.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

@Service
public class ProductService {
    private ProductRepo productRepo;
    private Supplier<Long> idGenerator;
    public ProductService(ProductRepo productRepo,Supplier<Long> idGenerator)
    {
        this.productRepo=productRepo;
        this.idGenerator=idGenerator;
    }


    public Map<String, Object> allProduct() {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            List<ProductEntity> productdata = productRepo.findAll();
            if (productdata.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("data", productdata);
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }


    public Map<String, Object> productById(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            ProductEntity obj = productRepo.findById(id).orElseThrow();
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

    public Map<String, Object> productProcess(ProductEntity product, MultipartFile file) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            System.out.println(product.getProduct_id());
            if (product.getProduct_id() > 0 && product.getProduct_id() != null) {
                Optional<ProductEntity> existingPrdOpt = productRepo.findById(product.getProduct_id());
                if (existingPrdOpt.isEmpty()) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Product not found");
                    return mapdata;
                }
                ProductEntity existingCat = existingPrdOpt.get();
                if (!existingCat.getProduct_name().equals(product.getProduct_name())) {
                    Boolean productAlreadyExists = productRepo.findByName(product.getProduct_name());
                    if (productAlreadyExists) {
                        mapdata.put("status", 201);
                        mapdata.put("msg", "Product already exists");
                        return mapdata;
                    }
                }

                if (file != null && !file.isEmpty()) {
                    String uploadDir = new File("src/main/resources/static/uploads/product/").getAbsolutePath();
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String originalName = file.getOriginalFilename();
                    String extension = "";
                    int dotIndex = originalName.lastIndexOf(".");
                    if (dotIndex > 0) {
                        extension = originalName.substring(dotIndex); // includes the dot, e.g. ".jpg"
                    }
                    String fileName = System.currentTimeMillis() + extension;
                    String filePath = uploadDir + File.separator + fileName;
                    file.transferTo(new File(filePath));

                    product.setProduct_image(fileName);   // add field mediaPath in your entity
                }


                productRepo.save(product);
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Updated Successfully");
                return mapdata;
            } else {
                Boolean prdExists = productRepo.findByName(product.getProduct_name());
                if (prdExists) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Product already exists");
                    return mapdata;
                } else {
                    if (file != null && !file.isEmpty()) {
                        String uploadDir = new File("src/main/resources/static/uploads/product/").getAbsolutePath();
                        File dir = new File(uploadDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        String originalName = file.getOriginalFilename();
                        String extension = "";
                        int dotIndex = originalName.lastIndexOf(".");
                        if (dotIndex > 0) {
                            extension = originalName.substring(dotIndex); // includes the dot, e.g. ".jpg"
                        }
                        String fileName = System.currentTimeMillis() + extension;
                        String filePath = uploadDir + File.separator + fileName;
                        file.transferTo(new File(filePath));
                        product.setProduct_image(fileName);   // add field mediaPath in your entity
                    }
                    product.setProduct_id(idGenerator.get());
                    product.setProduct_name(product.getProduct_name());
                    product.setProduct_slug(product.getProduct_slug());
                    productRepo.save(product);
                    mapdata.put("status", 200);
                    mapdata.put("msg", "Product added Succesffulyy");
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
            ProductEntity prd = productRepo.findById(id).orElseThrow();
            Integer currentStatus = prd.getStatus();
            Integer newStatus = (currentStatus == 1) ? 0 : 1;
            productRepo.updateStatusById(newStatus, id);
            mapdata.put("status", 200);
            mapdata.put("msg", "Status Updated Succesffulyy");
            return mapdata;
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> productDelete(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            ProductEntity prd=productRepo.findById(id).orElseThrow();
            int rowEffected = productRepo.deleteEntityById(id);
            if(rowEffected>0)
            {
                mapdata.put("status", 200);
                mapdata.put("msg", "Product Deleted Successfully");
                return mapdata;
            }else{
                mapdata.put("status", 201);
                mapdata.put("msg", "Something Went Wrong");
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }
}
