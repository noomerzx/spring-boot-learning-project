package com.project.homework.models;

import org.springframework.stereotype.Component;

@Component
public class CouponDiscountStretegyFactory {
  public CouponDiscountStregegy createCouponStretegy (String stretegyType) {
    CouponDiscountStregegy coupon;
    switch (stretegyType.toUpperCase()) {
      case "SPECIAL": 
        coupon = new CouponSpecialDiscount();
        break;
      case "PRICE": 
        coupon = new CouponPriceDiscount();
        break;
      case "QUANTITY":
        coupon = new CouponQuantityDiscount();
        break;
      default: 
        coupon = new CouponPriceDiscount();
        break;
    }
    return coupon;
  }
} 