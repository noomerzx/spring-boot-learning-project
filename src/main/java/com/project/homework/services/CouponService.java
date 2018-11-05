package com.project.homework.services;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.project.homework.entities.CouponEntity;
import com.project.homework.models.Coupon;
import com.project.homework.repositories.CouponRepository;
import com.project.homework.request.CreateCouponRequest;
import com.project.homework.request.UpdateCouponRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@Component
public class CouponService {

  @Autowired
  private CouponRepository couponRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<Coupon> getAll () {
    List<CouponEntity> couponE = couponRepository.findAll();
    Type resultType = new TypeToken<List<Coupon>>() {}.getType();
    return modelMapper.map(couponE, resultType);
  }

  public Coupon getById (Long couponId) {
    CouponEntity couponE = couponRepository.findById(couponId).get();
    return modelMapper.map(couponE, Coupon.class);
  }

  public Boolean create (CreateCouponRequest requestData) {
    try {
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      CouponEntity coupon = modelMapper.map(requestData, CouponEntity.class);
      String couponCode = RandomStringUtils.randomAlphabetic(10);
      coupon.setCouponCode(couponCode);
      coupon.setCreateTime(currentTime);
      coupon.setUpdateTime(currentTime);
      couponRepository.save(coupon);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean update (Long couponId, UpdateCouponRequest requestData) {
    try {
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      CouponEntity coupon = couponRepository.findById(couponId).get();
      coupon.setConditionAmount(requestData.conditionAmount);
      coupon.setUpdateTime(currentTime);
      couponRepository.save(coupon);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean DeductAvailability (Long couponId) {
    try {
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      CouponEntity coupon = couponRepository.findById(couponId).get();
      Integer availability = coupon.getAvailable() - 1;
      coupon.setAvailable(availability);
      coupon.setUpdateTime(currentTime);
      couponRepository.save(coupon);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean delete (Long couponId) {
    try {
      couponRepository.deleteById(couponId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}