package com.project.homework.models;

import com.project.homework.db2.entities.CouponEntity;

public interface CouponDiscountStregegy {
  public Float calculateDiscount(CouponEntity coupon, Float net, Integer itemCount);
}