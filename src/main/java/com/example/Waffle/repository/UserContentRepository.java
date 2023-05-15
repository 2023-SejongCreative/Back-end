package com.example.Waffle.repository;

import com.example.Waffle.entity.UserContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContentRepository extends JpaRepository<UserContentEntity, Long> {

}
