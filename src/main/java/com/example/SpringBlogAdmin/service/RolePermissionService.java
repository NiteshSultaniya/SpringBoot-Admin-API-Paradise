package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.RoleEntity;
import com.example.SpringBlogAdmin.repo.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

@Service
public class RolePermissionService {

    private final RoleRepo roleRepo;
    private final Supplier<Long> idGenerator;

    public RolePermissionService(RoleRepo roleRepo, Supplier<Long> idGenerator) {
        this.roleRepo = roleRepo;
        this.idGenerator = idGenerator;
    }


//   ************************ Role Service ************************

    public Map<String, Object> allRole() {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            List<RoleEntity> roleData = roleRepo.findAll();
            if (roleData.isEmpty()) {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Not Found");
                mapdata.put("data", new ArrayList<>());
                return mapdata;
            } else {
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Fetched SuccessFully");
                mapdata.put("data", roleData);
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }


    public Map<String, Object> roleById(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            RoleEntity obj = roleRepo.findById(id).orElseThrow();
            mapdata.put("status", 200);
            mapdata.put("msg", "Data Fetched SuccessFully");
            mapdata.put("data", obj);
            return mapdata;
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> roleProcess(RoleEntity role) {
        Map<String, Object> mapdata = new LinkedHashMap<>();

        try {
            System.out.println(role.getId());
            if (role.getId() > 0 && role.getId() != null) {
                Optional<RoleEntity> existingCatOpt = roleRepo.findById(role.getId());
                if (existingCatOpt.isEmpty()) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Role not found");
                    return mapdata;
                }
                RoleEntity existingCat = existingCatOpt.get();
                if (!existingCat.getRoleName().equals(role.getRoleName())) {
                    Boolean emailAlreadyExists = roleRepo.findByName(role.getRoleName());
                    if (emailAlreadyExists) {
                        mapdata.put("status", 201);
                        mapdata.put("msg", "Role already exists");
                        return mapdata;
                    }
                }
                roleRepo.save(role);
                mapdata.put("status", 200);
                mapdata.put("msg", "Data Updated Successfully");
                return mapdata;
            } else {
                Boolean catExists = roleRepo.findByName(role.getRoleName());
                if (catExists) {
                    mapdata.put("status", 201);
                    mapdata.put("msg", "Role already exists");
                    return mapdata;
                } else {
                    role.setId(idGenerator.get());
                    role.setRoleName(role.getRoleName());
                    roleRepo.save(role);
                    mapdata.put("status", 200);
                    mapdata.put("msg", "Role added Succesffulyy");
                    return mapdata;
                }
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> statusUpdate(Long id) {

        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            RoleEntity cat = roleRepo.findById(id).orElseThrow();
            Integer currentStatus = cat.getStatus();
            Integer newStatus = (currentStatus == 1) ? 0 : 1;
            roleRepo.updateStatusById(newStatus, id);
            mapdata.put("status", 200);
            mapdata.put("msg", "Status Updated Succesffulyy");
            return mapdata;
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

    public Map<String, Object> roleDelete(Long id) {
        Map<String, Object> mapdata = new LinkedHashMap<>();
        try {
            RoleEntity cat=roleRepo.findById(id).orElseThrow();
            int rowEffected = roleRepo.deleteEntityById(id);
            if(rowEffected>0)
            {
                mapdata.put("status", 200);
                mapdata.put("msg", "Role Deleted Successfully");
                return mapdata;
            }else{
                mapdata.put("status", 201);
                mapdata.put("msg", "Something Went Wrong");
                return mapdata;
            }
        } catch (Exception e) {
            mapdata.put("status", 400);
            mapdata.put("msg", e.getMessage());
            return mapdata;
        }
    }

//   ************************ Role Service End ************************

}
