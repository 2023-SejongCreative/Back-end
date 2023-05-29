package com.example.Waffle.repository;

import com.example.Waffle.entity.Group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findById(Long id);

    void deleteById(Long id);

}
