package domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class user {
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
    public user(Long id, String email, String password, String name, String introduction, String imgPath, String imgName){
         this.id = id;
         this.email = email;
         this.password = password;
         this.name = name;
         this.introduction = introduction;
         this.imgPath = imgPath;
         this.imgName = imgName;
     }



}
