package com.example.Waffle.repository;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.NoteEntity;
import com.example.Waffle.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findAllByGroup(GroupEntity group);

    List<NoteEntity> findAllByRoom(RoomEntity room);

    Optional<NoteEntity> findById(int id);

}
