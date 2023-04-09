package com.example.Waffle.repository;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findById(int id);

    Optional<GroupEntity> deleteById(int id);

}
