package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepo extends BaseRepository<ProductCategoryEntity,Long> {

    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.name=:name")
    Boolean findByName(@Param("name") String name);

    List<ProductCategoryEntity> findByStatus(int catStatus);

}
