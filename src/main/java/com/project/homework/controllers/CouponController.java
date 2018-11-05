package com.project.homework.controllers;

import java.util.HashMap;
import java.util.List;

import com.project.homework.models.Coupon;
import com.project.homework.request.CreateCouponRequest;
import com.project.homework.request.UpdateCouponRequest;
import com.project.homework.response.BaseResponse;
import com.project.homework.services.CouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coupons")
public class CouponController {
  @Autowired
  CouponService couponService;

  public BaseResponse<List<Coupon>> getCoupons () {
    BaseResponse<List<Coupon>> response = new BaseResponse<List<Coupon>>();
    response.data = couponService.getAll();
    response.code = "0";
    response.status = 200;
    return response;
  }

  public BaseResponse<Coupon> getCoupon (@PathVariable Long couponId) {
    BaseResponse<Coupon> response = new BaseResponse<Coupon>();
    response.data = couponService.getById(couponId);
    response.code = "0";
    response.status = 200;
    return response;
  }

  public BaseResponse<HashMap<String, Boolean>> createCoupon (@RequestBody CreateCouponRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", couponService.create(requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  public BaseResponse<HashMap<String, Boolean>> updateCoupon (@PathVariable Long couponId, @RequestBody UpdateCouponRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", couponService.update(couponId, requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }
  public BaseResponse<HashMap<String, Boolean>> deleteCoupon (@PathVariable Long couponId) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", couponService.delete(couponId));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }
}