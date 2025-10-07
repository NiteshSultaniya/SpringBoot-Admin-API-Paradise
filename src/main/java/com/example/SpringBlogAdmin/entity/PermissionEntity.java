package com.example.SpringBlogAdmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cs_permissions")
public class PermissionEntity {

    @Id
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_role_id")
    private Long permissionRoleId;

    @Column(name = "permission_type")
    private String permissionType;

    @Column(name = "permission_status")
    private Integer status=1;
}
