package com.project.homework.db2.repositories;

import com.project.homework.db2.entities.CouponEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long>{
  CouponEntity findByCouponCode(String couponCode);
  CouponEntity findByCouponType(String couponType);
  CouponEntity findByDiscountType(String discountType);
}
