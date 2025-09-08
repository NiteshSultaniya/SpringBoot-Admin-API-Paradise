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

@Table(name = "cs_users")
public class UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    @JsonProperty("user_name") // Add this annotation
    private String username;

    @Column(name="user_password")
    @JsonProperty(value = "user_password", access = JsonProperty.Access.WRITE_ONLY) // Add this annotation
    private String password;

    @Column(name="user_email")
    @JsonProperty("user_email") // Add this annotation
    private String email;

    @Column(name="user_status")
    private Integer status=1;

    @Column(name="user_address")
    @JsonProperty("user_address") // Add this annotation
    private String address;
}
