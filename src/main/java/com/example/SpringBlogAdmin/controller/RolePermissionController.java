package com.example.SpringBlogAdmin.controller;

import com.example.SpringBlogAdmin.entity.RoleEntity;
import com.example.SpringBlogAdmin.service.RolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/role-permission")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;
    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }



//   ************************ Role Controller************************
    @GetMapping("/role/all-role")
    public ResponseEntity<?> allRole() {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            Map<String, Object> obj = rolePermissionService.allRole();
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/role/find-role-by-id/{id}")
    public ResponseEntity<?> roleById(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            Map<String, Object> obj = rolePermissionService.roleById(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
    @PostMapping("/role/role-add-process")
    public ResponseEntity<?> roleProcess(@RequestBody RoleEntity roleEntity) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            Map<String, Object> obj = rolePermissionService.roleProcess(roleEntity);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }
    @GetMapping("/role/role-status-update/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = rolePermissionService.statusUpdate(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

    @GetMapping("/role/role-delete/{id}")
    public ResponseEntity<?> roleDelete(@PathVariable("id") Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            Map<String, Object> obj = rolePermissionService.roleDelete(id);
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return ResponseEntity.ok(mapdata);
        }
    }

//   ************************ Role Controller End ************************

}
