package com.project.homework.db1.repositories;

import com.project.homework.db1.entities.TokenHistoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenHistoryRepository extends JpaRepository<TokenHistoryEntity, Long>{
  TokenHistoryEntity findByUserId(Long userId);
  TokenHistoryEntity findByToken(String token);
}
