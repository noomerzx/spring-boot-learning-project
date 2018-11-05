package com.project.homework.repositories;

import com.project.homework.entities.TokenHistoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenHistoryRepository extends JpaRepository<TokenHistoryEntity, Long>{
  TokenHistoryEntity findByUserId(Long userId);
  TokenHistoryEntity findByToken(String token);
}
