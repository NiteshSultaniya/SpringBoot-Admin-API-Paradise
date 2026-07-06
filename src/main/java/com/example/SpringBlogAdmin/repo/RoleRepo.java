package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends BaseRepository<RoleEntity,Long> {

    @Query("SELECT COUNT(u) > 0 FROM #{#entityName} u WHERE u.roleName=:name")
    Boolean existsByRoleName(@Param("name") String name);

    RoleEntity findByRoleName(String roleName);

    List<RoleEntity> findByStatus(int status);
}
