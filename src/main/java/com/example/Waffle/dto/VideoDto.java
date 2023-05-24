package com.example.Waffle.dto;


import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private String sessionId;
    private int count;

    public VideoEntity toEntity(DmEntity dmId){
        VideoEntity videoEntity = new VideoEntity().builder()
                .dm(dmId)
                .sessionId(sessionId)
                .count(count)
                .build();

        return videoEntity;
    }
}
