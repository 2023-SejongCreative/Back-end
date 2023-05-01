package com.example.Waffle.repository;

import com.example.Waffle.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //email로 user 정보 찾기
    Optional<UserEntity> findByemail(String email);

    Optional<UserEntity> findByEmail(String name);

    Optional<UserEntity> findById(Long id);

    //이메일이 존재하는지 확인
    Boolean existsByemail(String email);

}
