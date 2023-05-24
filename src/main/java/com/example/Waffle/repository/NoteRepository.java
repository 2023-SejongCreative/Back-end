package com.example.Waffle.repository;

import com.example.Waffle.entity.Group.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.Room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findAllByGroupId(Long id);

    List<NoteEntity> findAllByRoomId(Long id);

    Optional<NoteEntity> findById(int id);

    void deleteById(int id);

}
