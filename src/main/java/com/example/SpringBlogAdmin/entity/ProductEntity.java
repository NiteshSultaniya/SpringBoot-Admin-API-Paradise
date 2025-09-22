package com.example.SpringBlogAdmin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cs_product")

public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_slug")
    private String productSlug;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "product_quantity_gms")
    private String productQuantityGms;

    @Column(name = "product_selling_price")
    private Integer productSellingPrice;

    @Column(name = "product_discount_price")
    private Integer productDiscountPrice;

    @Column(name = "product_mrp")
    private Integer productMrp;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_keyword")
    private String metaKeyword;

    @Column(name = "meta_description")
    private String metaDescription;

    @Column(name = "product_cat_id")
    private String productCatId;

    @Column(name = "status")
    private Integer status=1;

}
