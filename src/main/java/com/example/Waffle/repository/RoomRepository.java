package com.example.Waffle.repository;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    List<RoomEntity> findAllByGroupId(GroupEntity groupEntity);
}
