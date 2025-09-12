package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends BaseRepository<ProductEntity,Long> {
    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.name=:name")
    Boolean findByName(@Param("name") String name);
}
