package com.example.Waffle.repository;

import com.example.Waffle.entity.Room.UserRoomEntity;
import com.example.Waffle.entity.Room.UserRoomPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoomEntity, UserRoomPK> {

    Optional<UserRoomEntity> findByUserIdAndRoomId(Long userId, Long roomId);

    void deleteByRoomId(Long roomId);
}
