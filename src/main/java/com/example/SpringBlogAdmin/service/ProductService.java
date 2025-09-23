package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.ProductCategoryEntity;
import com.example.SpringBlogAdmin.entity.ProductEntity;
import com.example.SpringBlogAdmin.repo.ProductCategoryRepo;
import com.example.SpringBlogAdmin.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final Supplier<Long> idGenerator;
//    private final Supplier<Long> idGenerator;


    private final String product_image_path;

    public ProductService(ProductRepo productRepo,ProductCategoryRepo productCategoryRepo, Supplier<Long> idGenerator,@Value("${product_image_path}") String product_image_path) {
        this.productRepo = productRepo;
        this.idGenerator = idGenerator;
        this.product_image_path = product_image_path;
        this.productCategoryRepo = productCategoryRepo;
    }


    public Map<String, Object> allProduct(int page,int size) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<ProductEntity> productdata = productRepo.findAll(pageable);
            if (productdata.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("product_image_path", product_image_path);
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
            mapdata.put("product_image_path", product_image_path);
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
//                System.out.println(file);
//                return new LinkedHashMap<>();
        try {
            if (product.getId() > 0 && product.getId() != null) {
                Optional<ProductEntity> existingPrdOpt = productRepo.findById(product.getId());
                if (existingPrdOpt.isEmpty()) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Product not found");
                    return mapdata;
                }
                ProductEntity existingCat = existingPrdOpt.get();
                if (!existingCat.getProductName().equals(product.getProductName())) {
                    Boolean productAlreadyExists = productRepo.existsByProductName(product.getProductName());
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

                    product.setProductImage(fileName);   // add field mediaPath in your entity
                }
                productRepo.save(product);
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Updated Successfully");
                return mapdata;
            } else {
                Boolean prdExists = productRepo.existsByProductName(product.getProductName());

                System.out.println(prdExists.booleanValue());
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
                        product.setProductImage(fileName);   // add field mediaPath in your entity
                    }
                    product.setId(idGenerator.get());
                    product.setProductName(product.getProductName());
                    product.setProductSlug(product.getProductSlug());
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
            ProductEntity prd = productRepo.findById(id).orElseThrow();
            int rowEffected = productRepo.deleteEntityById(id);
            if (rowEffected > 0) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Product Deleted Successfully");
                return mapdata;
            } else {
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

    public Map<String, Object> getCategoryData() {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            List<ProductCategoryEntity> categoryData = productCategoryRepo.findByStatus(1);

            if (categoryData.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("data", categoryData);
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> getCategoryWiseProduct() {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            List<ProductCategoryEntity> category = productCategoryRepo.findByStatus(1);

            for(ProductCategoryEntity categoryy:category)
            {
//                System.out.println(categoryy.getCatProduct());
                List<ProductEntity> productData=productRepo.findProductByCategory(categoryy.getId());
                categoryy.setProduct(productData);
            }

            if (category.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("data", category);
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }
}
