package com.project.homework.Coupon;

import com.project.homework.repositories.CouponRepository;
import com.project.homework.services.CouponService;

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
  CouponRepository couponRepositoryMock;

	@InjectMocks
	CouponService couponService;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAll() {

	}

	@Test
	public void testGetById () {

	}

	@Test
	public void testCreate () {

	}

	@Test
	public void testUpdate () {

	}

	@Test
	public void testDeductAvailability () {

	}

	@Test
	public void testDelete () {

	}
}
