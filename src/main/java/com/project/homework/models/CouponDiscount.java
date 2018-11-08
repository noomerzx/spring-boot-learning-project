package com.project.homework.models;

public class CouponDiscount {
  public Float getDiscount(String couponType, Float couponDiscount, Float net) {
    Float discount = 0f;
    if (couponType.equals("PERCENT")) {
      discount = net * (couponDiscount/100);
    } else {
      discount = couponDiscount;
    }
    return discount;
  }
}