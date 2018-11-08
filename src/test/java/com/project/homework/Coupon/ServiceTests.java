package com.project.homework.Coupon;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.homework.db2.entities.CouponEntity;
import com.project.homework.db2.repositories.CouponRepository;
import com.project.homework.request.CreateCouponRequest;
import com.project.homework.request.UpdateCouponRequest;
import com.project.homework.services.CouponService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Mock
  CouponRepository couponRepositoryMock;

	@InjectMocks
	CouponService couponService;
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(couponService, "modelMapper", new ModelMapper());
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAll() {
		List<CouponEntity> coupons = new ArrayList<CouponEntity>();
		coupons.add(new CouponEntity());
		when(couponRepositoryMock.findAll()).thenReturn(coupons);
		assertEquals(coupons.size(), couponService.getAll().size());
	}

	@Test
	public void testGetById () {
		CouponEntity coupon = new CouponEntity();
		when(couponRepositoryMock.findById(any())).thenReturn(Optional.of(coupon));
		assertEquals(coupon.getId(), couponService.getById(1l).id);
	}

	@Test
	public void testCreate () {
		CreateCouponRequest requestData = new CreateCouponRequest();
		CouponEntity coupon = new CouponEntity();
		when(couponRepositoryMock.save(any())).thenReturn(coupon);
		assertEquals(couponService.create(requestData), true);
	}

	@Test
	public void testUpdate () {
		UpdateCouponRequest requestData = new UpdateCouponRequest();
		CouponEntity coupon = new CouponEntity();
		when(couponRepositoryMock.findById(any())).thenReturn(Optional.of(coupon));
		assertEquals(couponService.update(1l, requestData), true);
	}

	@Test
	public void testDeductAvailability () {
		CouponEntity coupon = new CouponEntity();
		coupon.setAvailable(1);
		when(couponRepositoryMock.findById(any())).thenReturn(Optional.of(coupon));
		when(couponRepositoryMock.save(any())).thenReturn(coupon);
		assertEquals(couponService.DeductAvailability(1l), true);
	}

	@Test
	public void testDelete () {
		doNothing().when(couponRepositoryMock).deleteById(any());
		assertEquals(couponService.delete(1l), true);
	}
}
