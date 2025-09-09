package com.example.SpringBlogAdmin.controller;


import com.example.SpringBlogAdmin.entity.MediaEntity;
import com.example.SpringBlogAdmin.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    private MediaService mediaService;

    public MediaController(MediaService mediaService){
        this.mediaService=mediaService;

    }
    @GetMapping("/all-media")
    public ResponseEntity<?> allMedia()
    {
         Map<String,Object> data=mediaService.allmedia();
        return ResponseEntity.ok(data);
    }

    @PostMapping("/add-media-process")
    public ResponseEntity<?> addmedia(@ModelAttribute MediaEntity media, @RequestParam("file") MultipartFile file)
    {
        System.out.println(file.getOriginalFilename());
        System.out.println(media.getId());
        return ResponseEntity.ok("success");
    }
}
