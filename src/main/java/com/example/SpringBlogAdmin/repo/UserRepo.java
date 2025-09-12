package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepo extends BaseRepository<UserEntity,Long> {

//    Boolean findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.username=:name")
    Boolean findByName(@Param("name") String name);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email=:email")
    Boolean findByEmail(@Param("email") String email);


    @Query("SELECT u.status FROM UserEntity u WHERE u.id=:id")
    Integer findStatusById(@Param("id") Long id);


}

