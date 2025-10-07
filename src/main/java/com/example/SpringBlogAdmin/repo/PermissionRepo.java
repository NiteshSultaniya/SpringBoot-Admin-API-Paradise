package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.PermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepo extends BaseRepository<PermissionEntity,Long> {

//    @Query("SELECT COUNT(u) > 0 FROM PermissionEntity u WHERE u.permissionRoleId=:permissionRoleId AND u.permissionType=:permissionType")
//    Boolean existsByPermissionRoleIdAndPermissionType(@Param("permissionRoleId") Long permissionRoleId,@Param("permissionType") String permissionType);
PermissionEntity findByPermissionRoleIdAndPermissionType(Long permissionRoleId, String permissionType);
}
