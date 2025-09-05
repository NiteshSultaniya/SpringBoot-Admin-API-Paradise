package com.example.SpringBlogAdmin.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
    @GetMapping
    public ResponseEntity<?> dashbordData()
    {
//        return
        Map<String,Object> data=new LinkedHashMap<>();
        data.put("status","Success");
        data.put("msg","Api Fetched Succeffuly");
        return ResponseEntity.ok(data) ;
    }
}
