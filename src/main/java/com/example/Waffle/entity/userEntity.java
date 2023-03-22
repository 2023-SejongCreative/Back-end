package com.example.Waffle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class userEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String introduction;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "img_name")
    private String imgName;

    @Builder
    public userEntity(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }




}
