package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.ProductCategoryEntity;
import com.example.SpringBlogAdmin.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends BaseRepository<ProductEntity,Long> {

//    Boolean exis(@Param("product_name") String product_name);
    Boolean existsByProductName(String productName);


    @Query(value = "SELECT * FROM cs_product p WHERE FIND_IN_SET(:id, p.product_cat_id)", nativeQuery = true)
    List<ProductEntity> findProductByCategory(@Param("id") Long id);
}
