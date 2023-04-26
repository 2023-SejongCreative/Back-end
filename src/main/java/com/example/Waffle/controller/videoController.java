package com.example.Waffle.controller;

import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class videoController {

    @Value("${openvidu.url}")
    private String openviduUrl;

    //앱이 OpenVidu 서버에 연결하고 사용자가 OpenVidu Dashboard에 액세스하는 데 사용되는 OpenVidu SECRET
    @Value("${openvidu.secret}")
    private String openviduSecret;

    private OpenVidu openvidu;

    @PostConstruct
    public void init(){

        this.openvidu = new OpenVidu(openviduUrl, openviduSecret);
    }

    //openvidu 서버에 새로운 session 생성
    @PostMapping("/chat/session")
    public ResponseEntity<String> initializeSession(@RequestBody Map<String, String> param)
            throws OpenViduJavaClientException, OpenViduHttpException {

            SessionProperties properties = SessionProperties.fromJson(param).build();
            Session session = this.openvidu.createSession(properties);

        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/chat/session/{session_id}/connect")
    public ResponseEntity<String> createConnection(@PathVariable("session_id") String sessionId,
                                                   @RequestBody Map<String, String> param)
        throws OpenViduJavaClientException, OpenViduHttpException{

        Session session = openvidu.getActiveSession(sessionId);
        if(session == null){
            throw new UserException(ErrorCode.CANT_FIND_SESSION);
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(param).build();
        //해당 세션에 유저를 연결
        Connection connection = session.createConnection(properties);

        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }


}
