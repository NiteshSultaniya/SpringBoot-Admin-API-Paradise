package com.example.SpringBlogAdmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cs_pincode")
public class PincodeEntity {

    @Id
    @Column(name = "pin_id")
    private Integer id;

    @Column(name = "PostOfficeName")
    private String postOfficeName;

    @Column(name = "Pincode")
    private Long pincode;


    @Column(name = "City")
    private String city;

    @Column(name = "District")
    private String district;

    @Column(name = "State")
    private String state;

    @Column(name = "pin_state_id")
    private Integer pinStateId;

    @Column(name = "pin_city_id")
    private Integer pinCityId;


}
