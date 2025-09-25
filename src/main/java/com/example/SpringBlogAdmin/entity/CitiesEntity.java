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
@Table(name = "cs_cities")
public class CitiesEntity {

    @Id
    @Column(name = "cs_cities")
    private Integer id;

    @Column(name = "cities_name")
    private String citiesName;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "cities_status")
    private Integer citiesStatus;

    @Column(name = "cities_popular")
    private Integer citiesPopular;

}
