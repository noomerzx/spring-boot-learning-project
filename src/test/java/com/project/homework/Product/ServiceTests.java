package com.project.homework.Product;

import com.project.homework.services.ProductService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.project.homework.entities.ProductEntity;
import com.project.homework.models.Product;
import com.project.homework.repositories.ProductRepository;
import com.project.homework.request.CreateProductRequest;
import com.project.homework.request.UpdateProductRequest;

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
  ProductRepository productRepositoryMock;

	@InjectMocks
  ProductService productService;
  
	@Test
	public void contextLoads() {
	}

  @Test
  public void testGetAll() {
    List<ProductEntity> mockList = new ArrayList<>();
    when(productRepositoryMock.findAll()).thenReturn(mockList);
    List<Product> result = productService.getAll();
		assertEquals(mockList.size(), result.size());
  }

  @Test
  public void testGetById() {
    ProductEntity productE = new ProductEntity();
    productE.setId(1l);
    when(productRepositoryMock.findById(any()).get()).thenReturn(productE);
    Product result = productService.getById(any());
    assertEquals(result.id, productE.getId());
  }

  @Test
  public void testCreate() {
    CreateProductRequest productData = new CreateProductRequest();
    productData.name = "p1";
    productData.cost = 100f;
    productData.price = 130f;
    Boolean result = productService.create(productData);
    assertEquals(result, true);
  }

  @Test
  public void testUpdate() {
    UpdateProductRequest productData = new UpdateProductRequest();
    ProductEntity productMock = new ProductEntity();
    when(productRepositoryMock.findById(any()).get()).thenReturn(productMock);
    productData.name = "update product";
    productData.cost = 150f;
    productData.price = 190f;
    Boolean result = productService.update(1l, productData);
    assertEquals(result, true);
  }

  @Test
  public void testDelete() {
    Boolean result = productService.delete(1l);
    assertEquals(result, true);
  }
}
