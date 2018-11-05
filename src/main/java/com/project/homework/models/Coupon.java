package com.project.homework.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
  public Long id;
  public String counponType;
  public Integer conditionAmount;
  public String discountType;
  public Float discount;
  public Integer available;
}