package com.project.homework.models;

import com.project.homework.db2.entities.CouponEntity;

public class CouponQuantityDiscount implements CouponDiscountStregegy {
  public Float calculateDiscount(CouponEntity coupon, Float net, Integer itemCount) {
    Float discount = 0f;
    if (itemCount > coupon.getConditionQuantity()) {
      return new CouponDiscount().getDiscount(coupon.getDiscountType(), coupon.getDiscount(), net);
    }
    return discount;
  }
}