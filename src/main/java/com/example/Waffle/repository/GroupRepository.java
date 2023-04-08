package com.example.Waffle.repository;

import com.example.Waffle.entity.GroupEntity;
import com.example.Waffle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {



}
