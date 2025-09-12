package com.example.SpringBlogAdmin.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cs_product_category")
public class ProductCategoryEntity {

    @Id
    @Column(name = "cat_id")
    @JsonProperty("cat_id")
    private Long id;

    @Column(name = "cat_name")
    @JsonProperty("cat_name")
    private String name;

    @Column(name = "cat_slug")
    @JsonProperty("cat_slug")
    private String slug;

    @Column(name = "status")
    @JsonProperty("status")
    private Integer status=1;
}
