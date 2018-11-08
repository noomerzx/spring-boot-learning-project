package com.project.homework.db1.repositories;

import com.project.homework.db1.entities.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
}