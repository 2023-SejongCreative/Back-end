package com.example.Waffle.repository;

import com.example.Waffle.entity.UserContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserContentRepository extends JpaRepository<UserContentEntity, Long> {

    List<UserContentEntity> findAllByUserId(Long userId);

    @Override
    Optional<UserContentEntity> findById(Long id);


    Optional<UserContentEntity> deleteById(int id);
}
