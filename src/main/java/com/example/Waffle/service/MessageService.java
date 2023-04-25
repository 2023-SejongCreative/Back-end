package com.example.Waffle.service;

import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public List<MessageEntity> getMessages(DmEntity dmEntity){
        return messageRepository.findAllByDmId(dmEntity.getId());
    }

}
