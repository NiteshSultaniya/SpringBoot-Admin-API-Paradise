package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.MediaEntity;
import com.example.SpringBlogAdmin.repo.MediaRepo;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MediaService {
    private final MediaRepo mediaRepo;

    public MediaService(MediaRepo mediaRepo) {
        this.mediaRepo = mediaRepo;
    }

    public Map<String, Object> allmedia() {
        Map<String, Object> mediaData = new LinkedHashMap<>();
        try {
            List<MediaEntity> mediadata = mediaRepo.findAll();
            if (mediadata.isEmpty()) {
                mediaData.put("Status", "Success");
                mediaData.put("data", "Data Not Found");
                return mediaData;
            }
            mediaData.put("Status", "Success");
            mediaData.put("data", mediadata);
            return mediaData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
