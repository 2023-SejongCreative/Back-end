package com.example.Waffle.controller;

import com.example.Waffle.dto.ProfileDto;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.UserRepository;
import com.example.Waffle.service.ProfileService;
import com.example.Waffle.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileService profileService;
    private final UserRepository userRepository;

    @PostMapping("/profile/update")
    public ResponseEntity<Object> updateProfile(@RequestParam(value = "img", required = false) MultipartFile image,
                                                @RequestParam(value = "introduction", required = false  ) String intro,
                                                @RequestHeader("access_token") String accessToken) throws IOException {

        String email = jwtTokenProvider.getEmail(accessToken);

        profileService.updateProfile(image, intro, email);


        return new ResponseEntity<>("프로필 수정 성공", HttpStatus.OK);
    }

    @GetMapping("/profile/me")
    public ResponseEntity<Object> myProfile(@RequestHeader("access_token") String accessToken){

        String email = jwtTokenProvider.getEmail(accessToken);
        Long id = profileService.emailToId(email);

        String profileDto = profileService.returnProfile(id);

        return new ResponseEntity<>(profileDto, HttpStatus.OK);

    }

    @GetMapping("/profile/other/{user_id}")
    public ResponseEntity<Object> otherProfile(@PathVariable("user_id") Long id){

        String profileDto = profileService.returnProfile(id);

        return new ResponseEntity<>(profileDto,HttpStatus.OK);
    }


    @PostMapping("/profile/content/create")
    public ResponseEntity<Object> createContent(@RequestHeader("access_token") String accessToken,
                                                @RequestBody Map<String, String> param) {

        String email = jwtTokenProvider.getEmail(accessToken);

        profileService.createContent(email,param.get("title"), param.get("detail"));

        return new ResponseEntity<>("content 생성 성공",HttpStatus.OK);
    }

    @PostMapping("/profile/{content_id}/update")
    public ResponseEntity<Object> updateContent(@PathVariable("content_id") Long contentId,
                                                @RequestBody Map<String, String> param){

        profileService.updateContent(contentId,param.get("title"), param.get("detail"));

        return new ResponseEntity<>("content 수정 성공",HttpStatus.OK);
    }

    @DeleteMapping("/profile/{content_id}/delete")
    public ResponseEntity<Object> deleteContent(@PathVariable("content_id") Long id){

        profileService.deleteContent(id);


        return new ResponseEntity<>("content 삭제 성공",HttpStatus.OK);
    }

}
