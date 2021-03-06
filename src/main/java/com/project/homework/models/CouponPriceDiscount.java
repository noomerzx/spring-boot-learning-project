package com.project.homework.models;

import com.project.homework.db2.entities.CouponEntity;

public class CouponPriceDiscount implements CouponDiscountStregegy {
  public Float calculateDiscount(CouponEntity coupon, Float net, Integer itemCount) {
    Float discount = 0f;
    if (net > coupon.getConditionPrice()) {
      return new CouponDiscount().getDiscount(coupon.getDiscountType(), coupon.getDiscount(), net);
    }
    return discount;
  }
}