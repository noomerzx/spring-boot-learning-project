package com.project.homework.Order;

import com.project.homework.services.OrderService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.homework.db2.entities.CouponEntity;
import com.project.homework.db1.entities.OrderEntity;
import com.project.homework.db1.entities.OrderItemEntity;
import com.project.homework.db1.entities.ProductEntity;
import com.project.homework.db1.entities.UserEntity;
import com.project.homework.models.Order;
import com.project.homework.models.OrderItem;
import com.project.homework.db2.repositories.CouponRepository;
import com.project.homework.db1.repositories.OrderRepository;
import com.project.homework.db1.repositories.ProductRepository;
import com.project.homework.db1.repositories.UserRepository;
import com.project.homework.request.CreateOrderRequest;
import com.project.homework.request.UpdateOrderRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

  @Mock
  OrderRepository orderRepositoryMock;
  
  @Mock
  ProductRepository productRepositoryMock;
  
  @Mock
  UserRepository userRepositoryMock;
  
  @Mock
	CouponRepository couponRepositoryMock;

	@InjectMocks
  OrderService orderService;
  
	@Test
	public void contextLoads() {
	}

  @Test
  public void testGetAll() {
    List<OrderEntity> mockList = new ArrayList<>();
    when(orderRepositoryMock.findAll()).thenReturn(mockList);
    List<Order> result = orderService.getAll();
		assertEquals(mockList.size(), result.size());
  }

  @Test
  public void testGetById() {
    OrderEntity orderE = new OrderEntity();
    List<OrderItemEntity> orderItems = new ArrayList<OrderItemEntity>();
    orderItems.add(new OrderItemEntity());
    orderE.setId(1l);
    orderE.setOrderItems(orderItems);
    when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(orderE));
    Order result = orderService.getById(any());
    assertEquals(result.id, orderE.getId());
  }

  @Test
  public void testCreateWithoutDiscount() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<OrderItem>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.orderItems = orderItems;
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(1l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testCreateWithDiscount() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.orderItems = orderItems;
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(3l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testCreateWithCouponPricePercent() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.couponCode = "ABC123";
    payloadMock.orderItems = orderItems;
    CouponEntity coupon = new CouponEntity();
    coupon.setCouponType("PRICE");
    coupon.setDiscountType("PERCENT");
    coupon.setConditionPrice(100f);
    coupon.setDiscount(50f);
    when(couponRepositoryMock.findByCouponCode(any())).thenReturn(coupon);
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(1l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testCreateWithCouponPriceFixed() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.couponCode = "ABC123";
    payloadMock.orderItems = orderItems;
    CouponEntity coupon = new CouponEntity();
    coupon.setCouponType("PRICE");
    coupon.setDiscountType("FIXED");
    coupon.setConditionPrice(100f);
    coupon.setDiscount(50f);
    when(couponRepositoryMock.findByCouponCode(any())).thenReturn(coupon);
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(1l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testCreateWithCouponQuantityPercent() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.couponCode = "ABC123";
    payloadMock.orderItems = orderItems;
    CouponEntity coupon = new CouponEntity();
    coupon.setCouponType("QUANTITY");
    coupon.setDiscountType("PERCENT");
    coupon.setConditionQuantity(2);
    coupon.setDiscount(50f);
    when(couponRepositoryMock.findByCouponCode(any())).thenReturn(coupon);
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(1l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testCreateWithCouponQuantityFixed() {
    UserEntity userMock = new UserEntity();
    OrderEntity orderE = new OrderEntity();
    ProductEntity productMock = new ProductEntity();
    CreateOrderRequest payloadMock = new CreateOrderRequest();
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(new OrderItem(1l, "p1", 100f, 0f, 2, 1l, null));
    orderItems.add(new OrderItem(2l, "p2", 150f, 0f, 1, 2l, null));
    payloadMock.status = "PENDING";
    payloadMock.net = 0f;
    payloadMock.discount = 0f;
    payloadMock.couponCode = "ABC123";
    payloadMock.orderItems = orderItems;
    CouponEntity coupon = new CouponEntity();
    coupon.setCouponType("QUANTITY");
    coupon.setDiscountType("FIXED");
    coupon.setConditionQuantity(2);
    coupon.setDiscount(50f);
    when(couponRepositoryMock.findByCouponCode(any())).thenReturn(coupon);
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    when(orderRepositoryMock.countByUser(any())).thenReturn(1l);
    when(productRepositoryMock.findById(any())).thenReturn(Optional.of(productMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderE);
    Boolean result = orderService.create(1l, payloadMock);
    assertEquals(result, true);
  }

  @Test
  public void testUpdate() {
    OrderEntity orderToUpdaterMock = new OrderEntity();
    UpdateOrderRequest orderData = new UpdateOrderRequest();
    orderData.status = "APPROVED";
    when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(orderToUpdaterMock));
    when(orderRepositoryMock.save(any())).thenReturn(orderToUpdaterMock);
    Boolean result = orderService.update(1l, orderData);
    assertEquals(result, true);
  }

  @Test
  public void testDelete() {
    Boolean result = orderService.delete(1l);
    assertEquals(result, true);
  }
}
