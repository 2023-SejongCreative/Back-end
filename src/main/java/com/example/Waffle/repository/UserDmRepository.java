package com.example.Waffle.repository;


import com.example.Waffle.entity.DM.UserDmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDmRepository extends JpaRepository<UserDmEntity, Long> {


}
