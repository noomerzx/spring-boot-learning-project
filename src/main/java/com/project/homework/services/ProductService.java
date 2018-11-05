package com.project.homework.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.project.homework.entities.ProductEntity;
import com.project.homework.models.Product;
import com.project.homework.repositories.ProductRepository;
import com.project.homework.request.CreateProductRequest;
import com.project.homework.request.UpdateProductRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductService {

  @Autowired
  ProductRepository productRepository;

  public List<Product> getAll () {
    List<ProductEntity> products = productRepository.findAll();
    return products.stream().map(item -> {
      Product product = new Product();
      product.id = item.getId();
      product.name = item.getName();
      product.price = item.getPrice();
      product.cost = item.getCost();
      product.stock = item.getStock();
      return product;
    }).collect(Collectors.toList());
  }

  public Product getById (Long productId) {
    ProductEntity p = productRepository.findById(productId).get();
    Product product = new Product();
    product.id = p.getId();
    product.name = p.getName();
    product.cost = p.getCost();
    product.price = p.getPrice();
    product.stock = p.getStock();
    return product;
  }

  public Boolean create (CreateProductRequest productData) {
    try {
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      ProductEntity product = new ProductEntity();
      product.setName(productData.name);
      product.setCost(productData.cost);
      product.setPrice(productData.price);
      product.setStock(0);
      product.setCreateTime(currentTime);
      product.setUpdateTime(currentTime);
      productRepository.save(product);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean update (Long productId, UpdateProductRequest productData) {
    try {
      ProductEntity product = productRepository.findById(productId).get();
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      product.setName(productData.name);
      product.setCost(productData.cost);
      product.setPrice(productData.price);
      product.setUpdateTime(currentTime);
      productRepository.save(product);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean updateStock (Long productId, Integer stock) {
    try {
      ProductEntity product = productRepository.findById(productId).get();
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      product.setStock(stock);
      product.setUpdateTime(currentTime);
      productRepository.save(product);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Boolean delete (Long productId) {
    try {
      productRepository.deleteById(productId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}