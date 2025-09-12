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
@Table(name = "cs_product")
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long product_id;

    @Column(name = "product_name")
    @JsonProperty("product_name")
    private String product_name;

    @Column(name = "product_slug")
    @JsonProperty("product_slug")
    private String product_slug;

    @Column(name = "product_description")
    @JsonProperty("product_description")
    private String product_description;

    @Column(name = "product_quantity")
    @JsonProperty("product_quantity")
    private Integer product_quantity;

    @Column(name = "product_quantity_gms")
    @JsonProperty("product_quantity_gms")
    private String product_quantity_gms;

    @Column(name = "product_selling_price")
    @JsonProperty("product_selling_price")
    private Integer product_selling_price;

    @Column(name = "product_discount_price")
    @JsonProperty("product_discount_price")
    private Integer product_discount_price;

    @Column(name = "product_mrp")
    @JsonProperty("product_mrp")
    private Integer product_mrp;

    @Column(name = "product_image")
    @JsonProperty("product_image")
    private String product_image;

    @Column(name = "meta_title")
    @JsonProperty("meta_title")
    private String meta_title;

    @Column(name = "meta_keyword")
    @JsonProperty("meta_keyword")
    private String meta_keyword;

    @Column(name = "meta_description")
    @JsonProperty("meta_description")
    private String meta_description;

    @Column(name = "status")
    @JsonProperty("status")
    private Integer status=1;
}
