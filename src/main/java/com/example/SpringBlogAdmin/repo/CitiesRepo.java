package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.CitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepo extends JpaRepository<CitiesEntity,Long> {
}
