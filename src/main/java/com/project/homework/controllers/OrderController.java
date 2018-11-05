package com.project.homework.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.project.homework.models.Order;
import com.project.homework.request.CreateOrderRequest;
import com.project.homework.request.UpdateOrderRequest;
import com.project.homework.response.BaseResponse;
import com.project.homework.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping("/")
  public BaseResponse<List<Order>> getOrders() {
    List<Order> orders = orderService.getAll();
    BaseResponse<List<Order>> response = new BaseResponse<List<Order>>();
    response.data = orders;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @GetMapping("/{orderId}")
  public BaseResponse<Order> getOrder(@PathVariable Long orderId) {
    Order order = orderService.getById(orderId);
    BaseResponse<Order> response = new BaseResponse<Order>();
    response.data = order;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PostMapping("/")
  public BaseResponse<HashMap<String, Boolean>> createOrder(HttpServletRequest request, @RequestBody CreateOrderRequest requestData) {
    HttpSession session = request.getSession();
    Long userId = (long)session.getAttribute("userId");
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", orderService.create(userId, requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PutMapping("/{orderId}")
  public BaseResponse<HashMap<String, Boolean>> updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", orderService.update(orderId, requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @DeleteMapping("/{orderId}")
  public BaseResponse<HashMap<String, Boolean>> deleteOrder(@PathVariable Long orderId) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", orderService.delete(orderId));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }
}