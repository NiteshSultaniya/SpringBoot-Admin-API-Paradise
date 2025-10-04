package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends BaseRepository<AdminEntity, Long> {


    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.username=:name")
    Boolean findByName(@Param("name") String name);

    @Query("SELECT COUNT(u) > 0 FROM AdminEntity u WHERE u.email=:email")
    Boolean findByEmail(@Param("email") String email);


    @Query("SELECT u.status FROM AdminEntity u WHERE u.id=:id")
    Integer findStatusById(@Param("id") Long id);


    AdminEntity findByUsername(String username);
}
