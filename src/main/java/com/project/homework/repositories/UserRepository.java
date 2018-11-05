package com.project.homework.repositories;

import com.project.homework.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
  UserEntity findByEmailAndPassword(String email, String password);
}