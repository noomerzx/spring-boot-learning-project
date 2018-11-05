package com.project.homework.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  public Long id;
  public String productName;
  public Float productPrice;
  public Float productDiscount;
  public Integer productQuantity;
  public Long productId;
  public List<Product> products;
}