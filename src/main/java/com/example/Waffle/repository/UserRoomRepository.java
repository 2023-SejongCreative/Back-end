package com.example.Waffle.repository;

import com.example.Waffle.entity.UserRoom.UserRoomEntity;
import com.example.Waffle.entity.UserRoom.UserRoomPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoomEntity, UserRoomPK> {

    Optional<UserRoomEntity> findByUserIdAndRoomId(Long userId, Long roomId);
}
