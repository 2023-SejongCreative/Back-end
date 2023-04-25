package com.example.Waffle.repository;

import com.example.Waffle.entity.DM.MessageEntity;
import com.example.Waffle.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByDmId(Long dmId);

}
