package com.example.Waffle.repository;

import com.example.Waffle.entity.UserRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoomEntity, Long> {
}
