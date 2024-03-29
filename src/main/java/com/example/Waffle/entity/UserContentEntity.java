package com.example.Waffle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="user_content")
public class UserContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public void changeTitleAndDetail(String title, String detail){
        if(!this.title.equals(title) && title != null)
            this.title = title;

        if(this.detail != null) {
            if (!this.detail.equals(detail))
                this.detail = detail;
        }
        else
            if(detail != null)
                this.detail = detail;
    }

    @Builder
    public UserContentEntity(String title, String detail, UserEntity user){
        this.title = title;
        this.detail = detail;
        this.user = user;
    }
}
