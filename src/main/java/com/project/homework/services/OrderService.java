package com.project.homework.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.project.homework.db2.entities.CouponEntity;
import com.project.homework.db1.entities.OrderEntity;
import com.project.homework.db1.entities.OrderItemEntity;
import com.project.homework.db1.entities.UserEntity;
import com.project.homework.models.CouponDiscountStregegy;
import com.project.homework.models.CouponDiscountStretegyFactory;
import com.project.homework.models.Order;
import com.project.homework.models.OrderItem;
import com.project.homework.db2.repositories.CouponRepository;
import com.project.homework.db1.repositories.OrderRepository;
import com.project.homework.db1.repositories.ProductRepository;
import com.project.homework.db1.repositories.UserRepository;
import com.project.homework.request.CreateOrderRequest;
import com.project.homework.request.UpdateOrderRequest;
import com.project.homework.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CouponRepository couponRepository;

  @Autowired
  private DateUtils dateUtil;

  @Autowired
  private CouponDiscountStretegyFactory couponDiscountStretegyFactory;

  public List<Order> getAll () {
    List<OrderEntity> orders = orderRepository.findAll();
    return orders.stream().map(o -> {
      Order order = new Order();
      order.id = o.getId();
      order.net = o.getNet();
      order.status = o.getStatus();
      order.discount = o.getDiscount();
      order.orderItems = o.getOrderItems().stream().map(item -> {
        OrderItem orderItem = new OrderItem();
        orderItem.id = item.getId();
        orderItem.productName = item.getProductName();
        orderItem.productPrice = item.getProductPrice();
        orderItem.productQuantity = item.getProductQuantity();
        orderItem.productDiscount = item.getProductDiscount();
        return orderItem;
      }).collect(Collectors.toList());
      return order;
    }).collect(Collectors.toList());
  }

  public Order getById (Long orderId) {
    OrderEntity o = orderRepository.findById(orderId).get();
    Order order = new Order();
    order.id = o.getId();
    order.discount = o.getDiscount();
    order.net = o.getNet();
    order.status = o.getStatus();
    order.orderItems = o.getOrderItems().stream().map(item -> {
      OrderItem orderItem = new OrderItem();
      orderItem.id = item.getId();
      orderItem.productName = item.getProductName();
      orderItem.productPrice = item.getProductPrice();
      orderItem.productQuantity = item.getProductQuantity();
      orderItem.productDiscount = item.getProductDiscount();
      return orderItem;
    }).collect(Collectors.toList());
    return order;
  }

  public Boolean create (Long userId, CreateOrderRequest orderData) {
    try {
      Timestamp currentTime = dateUtil.getCurrentTimestamp();
      OrderEntity order = new OrderEntity();
      UserEntity user = userRepository.findById(userId).get();
      Long orderHistoryCount = orderRepository.countByUser(user);
      Float net = (float)orderData.orderItems.stream().mapToDouble(i -> (i.productPrice * i.productQuantity)).sum();
      Integer itemCount = (int)orderData.orderItems.stream().mapToInt(i -> (1 * i.productQuantity)).sum();
      order.setOrderItems(orderData.orderItems.stream().map(item -> {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrder(order);
        orderItem.setProductDiscount(item.productDiscount);
        orderItem.setProductName(item.productName);
        orderItem.setProductPrice(item.productPrice);
        orderItem.setProductQuantity(item.productQuantity);
        orderItem.setProduct(productRepository.findById(item.productId).get());
        return orderItem;
      }).collect(Collectors.toList()));

      // discount by coupon
      CouponEntity coupon = couponRepository.findByCouponCode(orderData.couponCode);
      if (coupon != null && coupon.getAvailable() > 0) {
        CouponDiscountStregegy couponDiscount = couponDiscountStretegyFactory.createCouponStretegy(coupon.getCouponType());
        float totalDiscount = couponDiscount.calculateDiscount(coupon, net, itemCount);
        if (totalDiscount > 0) {
          net -= totalDiscount;
          order.setCouponCode(orderData.couponCode);
          coupon.setAvailable(coupon.getAvailable() - 1);
          couponRepository.save(coupon);
        }
      }

      // discount by history (10 percent)
      if (orderHistoryCount >= 3) {
        Float discount = (float)(net * 0.1);
        order.setDiscount(discount);
        order.setNet(net - discount);
      } else {
        order.setDiscount(orderData.discount);
        order.setNet(net);
      }

      
      order.setItemCount(itemCount);
      order.setUser(user);
      order.setStatus(orderData.status);
      order.setUpdateTime(currentTime);
      order.setCreateTime(currentTime);
      orderRepository.save(order);
      return true;
    } catch (Exception e) {
      throw e;
      // return false;
    }
  }

  public Boolean update (Long orderId, UpdateOrderRequest orderData) {
    try {
      Timestamp currentTime = dateUtil.getCurrentTimestamp();
      OrderEntity order = orderRepository.findById(orderId).get();
      order.setStatus(orderData.status);
      order.setUpdateTime(currentTime);
      orderRepository.save(order);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean delete (Long orderId) {
    try {
      orderRepository.deleteById(orderId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}