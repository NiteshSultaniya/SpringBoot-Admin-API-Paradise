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
    private final MediaService mediaService;

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
//        return ResponseEntity.ok(file.getContentType());
        Map<String,Object> data=mediaService.addmedia(file,media);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/status-update/{id}")
    public ResponseEntity<?> statusUpdate(@PathVariable Long id)
    {
        Map<String,Object> data=mediaService.statusUpdate(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("delete-media/{id}")
    public ResponseEntity<?> deleteMedia(@PathVariable Long id)
    {
        Map<String,Object> data=mediaService.deleteMedia(id);
        return ResponseEntity.ok(data);
    }}
