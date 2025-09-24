package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<CountryEntity,Long> {
}
