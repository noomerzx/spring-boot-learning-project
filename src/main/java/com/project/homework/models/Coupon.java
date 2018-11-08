package com.project.homework.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
  public Long id;
  public String counponType;
  public Float conditionPrice;
  public Integer conditionQuantity;
  public String discountType;
  public Float discount;
  public Integer available;
}