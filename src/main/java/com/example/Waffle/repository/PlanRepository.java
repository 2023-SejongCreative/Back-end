package com.example.Waffle.repository;

import com.example.Waffle.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    List<PlanEntity> findAllByUserId(Long id);

    List<PlanEntity> findAllByGroupId(Long id);

    List<PlanEntity> findAllByRoomId(Long id);

    Optional<PlanEntity> findById(Long id);

    Optional<PlanEntity> deleteById(int id);

}
