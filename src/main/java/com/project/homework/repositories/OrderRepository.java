package com.project.homework.repositories;

import java.util.List;

import com.project.homework.entities.OrderEntity;
import com.project.homework.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
  List<OrderEntity> findByUser(UserEntity user);
  Long countByUser(UserEntity user);
}
