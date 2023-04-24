package com.example.Waffle.repository;

import com.example.Waffle.entity.DM.DmEntity;
import com.example.Waffle.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmRepository extends JpaRepository<DmEntity, Long> {



}
