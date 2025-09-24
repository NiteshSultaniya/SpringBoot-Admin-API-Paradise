package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.PincodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeRepo extends JpaRepository<PincodeEntity,Long> {
}
