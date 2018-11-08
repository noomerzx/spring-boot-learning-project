package com.project.homework.db1.repositories;

import com.project.homework.db1.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
  UserEntity findByEmailAndPassword(String email, String password);
}