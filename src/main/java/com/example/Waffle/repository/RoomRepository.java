package com.example.Waffle.repository;

import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    List<RoomEntity> findAllByGroup(GroupEntity groupEntity);

    Optional<RoomEntity> findById(Long roomId);
}
