package com.example.SpringBlogAdmin.repo;

import com.example.SpringBlogAdmin.config.BaseRepository;
import com.example.SpringBlogAdmin.entity.MediaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends BaseRepository<MediaEntity,Long> {
}
