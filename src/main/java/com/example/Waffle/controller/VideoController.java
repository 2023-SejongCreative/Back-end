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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VideoController {

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
    @ResponseBody
    public ResponseEntity<Object> initializeSession(@RequestBody Map<String, String> param)
            throws OpenViduJavaClientException, OpenViduHttpException {

            SessionProperties properties = SessionProperties.fromJson(param).build();
            Session session = this.openvidu.createSession(properties);

        System.out.println("[" + session.getSessionId());

        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/chat/session/{session_id}/connect")
    @ResponseBody
    public ResponseEntity<Object> createConnection(@PathVariable("session_id") String sessionId,
                                                   @RequestBody Map<String, String> param)
        throws OpenViduJavaClientException, OpenViduHttpException{

        Session session = openvidu.getActiveSession(sessionId);
        if(session == null){
            throw new UserException(ErrorCode.CANT_FIND_SESSION);
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(param).build();
        //해당 세션에 유저를 연결
        Connection connection = session.createConnection(properties);

        String url = connection.getToken();
        System.out.println(url);

        return new ResponseEntity<>(url, HttpStatus.OK);
    }

   /*-----test 용------*/
//    @PostMapping("/api/sessions")
//    @ResponseBody
//    public ResponseEntity<Object> initialize(@RequestBody Map<String, String> param)
//            throws OpenViduJavaClientException, OpenViduHttpException {
//
//        SessionProperties properties = SessionProperties.fromJson(param).build();
//        Session session = this.openvidu.createSession(properties);
//
//        System.out.println("[" + session.getSessionId());
//
//        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
//    }
//
//    @PostMapping("/api/sessions/{sessionId}/connections")
//    @ResponseBody
//    public ResponseEntity<Object> connection(@PathVariable("sessionId") String sessionId,
//                                                   @RequestBody Map<String, String> param)
//            throws OpenViduJavaClientException, OpenViduHttpException {
//
//        Session session = openvidu.getActiveSession(sessionId);
//        if (session == null) {
//            throw new UserException(ErrorCode.CANT_FIND_SESSION);
//        }
//
//        ConnectionProperties properties = ConnectionProperties.fromJson(param).build();
//        //해당 세션에 유저를 연결
//        Connection connection = session.createConnection(properties);
//
//        String url = connection.getToken();
//        System.out.println(url);
//
//        return new ResponseEntity<>(url, HttpStatus.OK);
//    }

}
