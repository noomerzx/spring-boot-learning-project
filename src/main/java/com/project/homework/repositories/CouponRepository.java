package com.project.homework.repositories;

import com.project.homework.entities.CouponEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long>{
  CouponEntity findByCouponCode(String couponCode);
  CouponEntity findByCouponType(String couponType);
  CouponEntity findByDiscountType(String discountType);
}
