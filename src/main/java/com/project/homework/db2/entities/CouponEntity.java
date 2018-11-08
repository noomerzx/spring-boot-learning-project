package com.project.homework.db2.entities;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Coupon")
@Table(name = "t_coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String couponCode;
    private String couponType;
    private Integer conditionQuantity;
    private Float conditionPrice;
    private String discountType;
    private Float discount;
    private Integer available;
    private Timestamp createTime;
    private Timestamp updateTime;
}