package com.example.Waffle.entity;

import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.entity.Group.UserGroupEntity;
import com.example.Waffle.entity.Room.UserRoomEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
     private String email;

     private String password;

     private String name;

     private String introduction;

     @Column(name = "image")
     private String image;

     @OneToMany(mappedBy = "user")
     private List<UserGroupEntity> userGroup = new ArrayList<>();
     
     @OneToMany(mappedBy = "user")
     private List<UserRoomEntity> userRoom = new ArrayList<>();

     @OneToMany(mappedBy = "user")
     private List<MessageEntity> message = new ArrayList<>();

     @OneToMany(mappedBy = "user")
     private List<PlanEntity> plan = new ArrayList<>();

     @OneToMany(mappedBy = "user")
     private List<UserContentEntity> userContent = new ArrayList<>();

     @OneToMany(mappedBy = "user")
     private List<NoteEntity> note = new ArrayList<>();



     @Builder
    public UserEntity(String email, String password, String name){
         this.email = email;
         this.password = password;
         this.name = name;
     }


     @Autowired
    public String getEmail(){
         return email;
     }

     @Autowired
    public String getPassword(){
         return password;
     }

    public void changeIntroduction(String introduction){
        this.introduction = introduction;
    }

    public void changeImage(String image){
        this.image = image;
    }


}
