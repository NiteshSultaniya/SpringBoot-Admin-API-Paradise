package com.example.SpringBlogAdmin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String to;
    private String subject;
    private String message;


}
