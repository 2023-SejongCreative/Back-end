package com.example.Waffle.service;

import com.example.Waffle.dto.ChatDto;
import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.exception.ErrorCode;
import com.example.Waffle.exception.UserException;
import com.example.Waffle.repository.DmRepository;
import com.example.Waffle.repository.MessageRepository;
import com.example.Waffle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final DmRepository dmRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<MessageEntity> getMessages(DmEntity dmEntity){
        return messageRepository.findAllByDmId(dmEntity.getId());
    }

    @Transactional
    public void saveMessage(ChatDto chatDto){
        UserEntity userEntity = userRepository.findByEmail(chatDto.getSender())
                .orElseThrow(() -> new UserException(ErrorCode.NO_USER));
        DmEntity dmEntity = dmRepository.findById(chatDto.getDmId())
                .orElseThrow(() -> new UserException(ErrorCode.NO_DM));

        new MessageEntity();
        MessageEntity messageEntity = MessageEntity.builder()
                .chat(chatDto.getContent())
                .user(userEntity)
                .dm(dmEntity)
                .time(chatDto.getTime())
                .build();

        messageRepository.save(messageEntity);

    }


    @Transactional
    public String messageList(int dmId){
        DmEntity dmEntity = dmRepository.findById(dmId)
                .orElseThrow(()-> new UserException(ErrorCode.NO_DM));

        JSONObject messageList = new JSONObject();
        try{
            List<MessageEntity> messageEntities = messageRepository.findAllByDmId(dmEntity.getId());
            JSONArray messageArr = new JSONArray();
            for(MessageEntity messageEntity : messageEntities){
                JSONObject message = new JSONObject();
                message.put("chat", messageEntity.getChat());
                message.put("time", messageEntity.getTime());
                message.put(("user_email"), messageEntity.getUserEmail());
                message.put(("user_name"), messageEntity.getUserName());
                messageArr.put(message);
            }
            messageList.put("messages", messageArr);
        }catch (Exception e){
            throw new UserException(ErrorCode.CANT_FINDDMUSER);
        }
        return messageList.toString();
    }

    @Transactional
    public ChatDto createChatDto(ChatDto chatDto){

        chatDto.setTime(LocalDateTime.now());

        return chatDto;
    }

}
