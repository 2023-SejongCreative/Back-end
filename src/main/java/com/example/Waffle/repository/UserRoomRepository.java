package com.example.Waffle.repository;

import com.example.Waffle.entity.UserRoom.UserRoomEntity;
import com.example.Waffle.entity.UserRoom.UserRoomPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoomEntity, UserRoomPK> {
}
