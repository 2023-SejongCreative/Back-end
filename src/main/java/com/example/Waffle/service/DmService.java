package com.example.Waffle.service;

import com.example.Waffle.dto.DmDto;
import com.example.Waffle.dto.UserDmDto;
import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.entity.DM.UserDmEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.DmRepository;
import com.example.Waffle.repository.UserDmRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DmService {

    private final DmRepository dmRepository;
    private final UserRepository userRepository;
    private final UserDmRepository userDmRepository;
    private final MessageService messageService;
    @Transactional
    public Long createDm(DmDto dmDto, List<String> emails){

        if(dmDto.getCount() > 6)
            new UserException(ErrorCode.TOO_MANY_PEOPLE);

        DmEntity dmEntity = dmDto.toEntity();

        this.dmRepository.save(dmEntity);

        for(String email : emails){
            UserEntity userEntity = this.userRepository.findByemail(email)
                    .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

            UserDmEntity userDmEntity = new UserDmEntity(userEntity, dmEntity);
            this.userDmRepository.save(userDmEntity);
        }


        return dmEntity.getId();
    }

    @Transactional
    public void inviteDm(int dmId, String email){

        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));

        DmEntity dmEntity = dmRepository.findById(dmId)
                .orElseThrow(() -> new UserException(ErrorCode.NO_DM));

        Optional<UserDmEntity> userDmEntity = userDmRepository.findByUserIdAndDmId(userEntity.getId(), dmEntity.getId());
        if(userDmEntity.isPresent()){
            throw new UserException(ErrorCode.DUPLICATE_DM_USER);
        }
        else{
            UserDmDto userDmDto = new UserDmDto(userEntity, dmEntity);
            userDmRepository.save(userDmDto.toEntity());
            int count = dmEntity.getCount() + 1;
            dmEntity.builder()
                    .count(count)
                    .build();
        }

    }

    @Transactional
    public String dmUserList(int dmId){
        DmEntity dmEntity = dmRepository.findById(dmId)
                .orElseThrow(()-> new UserException(ErrorCode.NO_DM));

        JSONObject userList = new JSONObject();
        try{
            List<UserDmEntity> userDmEntities = userDmRepository.findByDmId(dmId);
            JSONArray userArr = new JSONArray();
            for(UserDmEntity userDmEntity : userDmEntities){
                JSONObject user = new JSONObject();
                UserEntity userEntity = userDmEntity.getUser();
                user.put("name", userEntity.getName());
                user.put("id", userEntity.getId());
                userArr.put(user);
            }
            userList.put("users", userArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FINDDMUSER);
        }
        return userList.toString();
    }

    @Transactional
    public String dmList(String email) {
        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(()-> new UserException(ErrorCode.NO_USER));

        JSONObject dmList = new JSONObject();
        try{
            List<UserDmEntity> userDmEntities = userDmRepository.findByUserId(userEntity.getId());
            JSONArray dmArr = new JSONArray();
            for(UserDmEntity userDmEntity : userDmEntities){
                JSONObject dm = new JSONObject();
                DmEntity dmEntity = new UserDmEntity().getDm();
                dm.put("name", dmEntity.getName());
                dm.put("id", dmEntity.getId());
                dm.put("last_chat", dmEntity.getLast_chat());
                dmArr.put(dm);
            }
            dmList.put("dms", dmArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FINDUSERDM);
        }
        return dmList.toString();
    }


    @Transactional
    public void leaveChat(String email, int dmId){

        try{
            DmEntity dmEntity = dmRepository.findById(dmId)
                    .orElseThrow(()->new UserException(ErrorCode.NO_DM));

            UserEntity userEntity = userRepository.findByemail(email)
                    .orElseThrow(()->new UserException(ErrorCode.NO_USER));

            if(dmEntity.getCount()<=1){
                List<MessageEntity> messageEntities = messageService.getMessages(dmEntity);

                for(MessageEntity messageEntity : messageEntities){
                    //message 삭제
                }

                userDmRepository.deleteByUserIdAndDmId(userEntity.getId(), dmEntity.getId());
                dmRepository.deleteById(dmEntity.getId());

            }
            else{
                userDmRepository.deleteByUserIdAndDmId(userEntity.getId(), dmEntity.getId());
                int count = dmEntity.getCount() -1;
                dmEntity.builder()
                        .count(count)
                        .build();
            }


        }


    }

}
