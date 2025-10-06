package com.example.SpringBlogAdmin.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T,ID> {

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} u SET u.status = :status WHERE u.id = :id")
    int updateStatusById(@Param("status") Integer status, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} u WHERE u.id = :id")
    int deleteEntityById(@Param("id") Long id); // Returns number of affected rows

    @Query("SELECT u FROM #{#entityName} u WHERE u.status = 1")
    List<T> activeStatusData(); // Returns number of affected rows
}
