package com.example.Waffle.service;

import com.example.Waffle.dto.VideoDto;
import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.VideoEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.DmRepository;
import com.example.Waffle.repository.VideoRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final DmRepository dmRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public String createSession(OpenVidu openvidu, Map<String, String> param)
            throws OpenViduJavaClientException, OpenViduHttpException {

        int dmId = Integer.parseInt(param.get("id"));

        DmEntity dmEntity = dmRepository.findById(dmId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_DM));

        Optional<VideoEntity> videoEntity = videoRepository.findById(dmId);

        //이미 만들어진 세션이 없으면
        if(videoEntity.isEmpty()){

            //세션 생성
            SessionProperties properties = SessionProperties.fromJson(param).build();
            Session session = openvidu.createSession(properties);
            String sessionId = session.getSessionId();

            //세션 정보 저장
            VideoDto videoDto = new VideoDto(sessionId, 0);
            VideoEntity video = videoDto.toEntity(dmEntity);
            videoRepository.save(video);

            return sessionId;
        }
        //이미 만들어진 세션이 있으면
        else{
            return videoEntity.get().getSessionId();
        }

    }
}
