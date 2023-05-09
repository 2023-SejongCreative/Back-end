package com.example.Waffle.entity.DM;

import jakarta.persistence.*;
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
    @JoinColumn(name = "dm_id")
    private DmEntity dm;

    @Column(name = "session_id")
    private String sessionId;

    private int count;

}
