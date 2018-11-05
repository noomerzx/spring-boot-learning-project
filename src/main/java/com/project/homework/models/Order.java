package com.project.homework.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Order {
  public Long id;
  public String couponCode;
  public String status;
  public Float net;
  public Float discount;
  public List<OrderItem> orderItems;
}