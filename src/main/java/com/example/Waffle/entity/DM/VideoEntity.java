package com.example.Waffle.entity.DM;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "videochat")
public class VideoEntity {

    @Id
    @Column(name = "dm_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "dm_id")
    private DmEntity dmEntity;

    @Column(name = "session_id")
    private String sessionId;

    private int count;

    @Builder
    private VideoEntity(DmEntity dm, String sessionId, int count){
        this.dmEntity = dm;
        this.sessionId = sessionId;
        this.count = count;
    }

    public void changeCount(int count){
        this.count = count;
    }

}
