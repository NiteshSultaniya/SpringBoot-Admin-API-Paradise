package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.MediaEntity;
import com.example.SpringBlogAdmin.repo.MediaRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MediaService {
    private final MediaRepo mediaRepo;

    private final String media_image_path;
    public MediaService(MediaRepo mediaRepo,@Value("${media_image_path}")String media_image_path) {
        this.mediaRepo = mediaRepo;
        this.media_image_path=media_image_path;
    }

    public Map<String, Object> allmedia() {
        Map<String, Object> mediaData = new LinkedHashMap<>();
        try {
            List<MediaEntity> mediadata = mediaRepo.findAll();
            if (mediadata.isEmpty()) {
                mediaData.put("status", 200);
                mediaData.put("data", "Data Not Found");
                return mediaData;
            }
            mediaData.put("status", 200);
            mediaData.put("media_image_path",media_image_path);
            mediaData.put("data", mediadata);
            return mediaData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> addmedia(MultipartFile file, MediaEntity media) {
        Map<String, Object> mediaData = new LinkedHashMap<>();
        try {
            if (file != null && !file.isEmpty()) {
                String uploadDir = new File("src/main/resources/static/uploads/").getAbsolutePath();
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String originalName = file.getOriginalFilename();
                String extension = "";
                int dotIndex = originalName.lastIndexOf(".");
                if (dotIndex > 0) {
                    extension = originalName.substring(dotIndex); // includes the dot, e.g. ".jpg"
                }
                String fileName = System.currentTimeMillis() + extension;
                String filePath = uploadDir+File.separator + fileName;
                file.transferTo(new File(filePath));
                media.setMedia_url(fileName);   // add field mediaPath in your entity
                media.setMedia_name(media.getMedia_name());   // add field mediaPath in your entity

                mediaRepo.save(media);
            }
            else {
                mediaData.put("status", 201);
                mediaData.put("msg", "File Is Empty");
                return mediaData;
            }

            mediaData.put("status", 200);
            mediaData.put("msg", "Media Uploaded Sucessfully");
            return mediaData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
