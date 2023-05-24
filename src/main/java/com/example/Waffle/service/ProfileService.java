package com.example.Waffle.service;

import com.example.Waffle.dto.ContentDto;
import com.example.Waffle.dto.ProfileDto;
import com.example.Waffle.entity.UserContentEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.UserContentRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserContentRepository userContentRepository;

    @Transactional
    public void updateProfile(String image, String intro, String email){

    UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

    if(image != null)
        userEntity.changeImage(image);
    else if(userEntity.getImage() != null)
        userEntity.changeImage(null);

    userEntity.changeIntroduction(intro);

    userRepository.save(userEntity);

    }

    public String returnProfile(Long id){

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()->new UserException(ErrorCode.NO_USER));

        JSONObject contentList = new JSONObject();
        try {
            List<UserContentEntity> userContentEntities = userContentRepository.findAllByUserId(userEntity.getId());
            JSONArray contentArr = new JSONArray();
            for(UserContentEntity contentEntity : userContentEntities){
                JSONObject content = new JSONObject();
                content.put("id", contentEntity.getId());
                content.put("title",contentEntity.getTitle());
                content.put("detail",contentEntity.getDetail());
                contentArr.put(content);
            }
            contentList.put("content",contentArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FIND_CONTENT);
        }

        contentList.put("name", userEntity.getName());
        contentList.put("introduction",userEntity.getIntroduction());
//        if(!ObjectUtils.isEmpty(userEntity.getImage())){
//            String imageData = userEntity.getImage();
//
//            String imageBase64 = Base64.getEncoder().encodeToString(imageData);
//
//            contentList.put("img",imageBase64);
//        }

        contentList.put("img",userEntity.getImage());
        return contentList.toString();
    }

    @Transactional
    public void createContent(String email, String title, String detail){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException(ErrorCode.NO_USER));

        ContentDto contentDto = new ContentDto(title, detail, userEntity);
        UserContentEntity userContentEntity = contentDto.toEntity();

        userContentRepository.save(userContentEntity);

    }

    @Transactional
    public void updateContent(Long id, String title, String detail){
        UserContentEntity userContentEntity = userContentRepository.findById(id)
                .orElseThrow(()->new UserException(ErrorCode.NO_CONTENT));

        userContentEntity.changeTitleAndDetail(title, detail);

    }

    @Transactional
    public void deleteContent(Long id){
        UserContentEntity userContentEntity = userContentRepository.findById(id)
                .orElseThrow(()->new UserException(ErrorCode.NO_CONTENT));

        userContentRepository.deleteById(id);
    }

    public Long emailToId(String email){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException(ErrorCode.NO_USER));

        return userEntity.getId();
    }

    public String contentList(UserEntity userEntity){

        JSONObject contentList = new JSONObject();
        try {
            List<UserContentEntity> userContentEntities = userContentRepository.findAllByUserId(userEntity.getId());
            JSONArray contentArr = new JSONArray();
            for(UserContentEntity contentEntity : userContentEntities){
                JSONObject content = new JSONObject();
                content.put("id", contentEntity.getId());
                content.put("title",contentEntity.getTitle());
                content.put("detail",contentEntity.getDetail());
                contentArr.put(content);
            }
            contentList.put("content",contentArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FIND_CONTENT);
        }

        return contentList.toString();
    }

}
