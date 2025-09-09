package com.example.SpringBlogAdmin.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cs_media")
public class MediaEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "media_name")
    @JsonProperty("media_name") // Add this annotation
    private String name;

    @Column(name = "media_status")
    private Integer status=1;

}
