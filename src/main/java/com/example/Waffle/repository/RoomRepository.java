package com.example.Waffle.repository;

import com.example.Waffle.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {


}
