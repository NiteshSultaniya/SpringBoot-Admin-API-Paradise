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
@Table(name = "cs_countries")
public class CountryEntity {

    @Id
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "country_shortname")
    private String countryShortname;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_phonecode")
    private Long countryPhonecode;

}
