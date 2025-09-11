package com.example.SpringBlogAdmin.service;

import com.example.SpringBlogAdmin.entity.MediaEntity;
import com.example.SpringBlogAdmin.repo.MediaRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class MediaService {
    private final MediaRepo mediaRepo;

    private final String media_image_path;

    public MediaService(MediaRepo mediaRepo, @Value("${media_image_path}") String media_image_path) {
        this.mediaRepo = mediaRepo;
        this.media_image_path = media_image_path;
    }

    public Map<String, Object> allmedia() {
        Map<String, Object> mediaData = new LinkedHashMap<>();
        try {
            List<MediaEntity> mediadata = mediaRepo.findAll();
            if (mediadata.isEmpty()) {
                mediaData.put("status", 200);
                mediaData.put("data", new ArrayList<>());
                return mediaData;
            }
            mediaData.put("status", 200);
            mediaData.put("media_image_path", media_image_path);
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
                String uploadDir = new File("src/main/resources/static/uploads/media/").getAbsolutePath();
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
                String filePath = uploadDir + File.separator + fileName;
                file.transferTo(new File(filePath));
                media.setMedia_url(fileName);   // add field mediaPath in your entity
                media.setMedia_name(media.getMedia_name());   // add field mediaPath in your entity

                mediaRepo.save(media);
            } else {
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

    public Map<String, Object> statusUpdate(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            MediaEntity mediaExists=mediaRepo.findById(id).orElseThrow();
            Integer status = mediaExists.getStatus();
            Integer updatedStatus=status==1?0:1;
            int res = mediaRepo.updateStatusById(updatedStatus, id);
            if (res > 0) {
                response.put("status", 200);
                response.put("msg", "Media Status Updated Sucessfully");
            } else {
                response.put("status", 201);
                response.put("msg", "Something Went Wrong");
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String, Object> deleteMedia(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            MediaEntity media=mediaRepo.findById(id).orElseThrow();
            String path=new File("src/main/resources/static/uploads/media").getAbsolutePath();
            String mediaurl=media.getMedia_url();

            File file = new File(path+"/" + mediaurl);
                System.out.println(file);
            if(file.exists() && file.delete())
            {
                int res = mediaRepo.deleteEntityById(id);
                if (res > 0) {
                    response.put("status", 200);
                    response.put("msg", "Media Deleted Sucessfully");
                }
            }
            else {
                response.put("status", 201);
                response.put("msg", "Something Went Wrong");
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
