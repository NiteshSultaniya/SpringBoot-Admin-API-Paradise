package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity,Integer> {

//    List<UserEntity> findAllByOrderByCreatedAtDesc(String Keyword);
    AdminEntity findByUsername(String username);
}
