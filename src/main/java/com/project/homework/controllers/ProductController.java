package com.project.homework.controllers;

import java.util.HashMap;
import java.util.List;

import com.project.homework.models.Product;
import com.project.homework.request.CreateProductRequest;
import com.project.homework.request.UpdateProductRequest;
import com.project.homework.response.BaseResponse;
import com.project.homework.services.ProductService;

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
@RequestMapping("products")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping("/")
  public BaseResponse<List<Product>> getProducts() {
    List<Product> products = productService.getAll();
    BaseResponse<List<Product>> response = new BaseResponse<List<Product>>();
    response.data = products;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @GetMapping("/{productId}")
  public BaseResponse<Product> getUser(@PathVariable Long productId) {
    Product product = productService.getById(productId);
    BaseResponse<Product> response = new BaseResponse<Product>();
    response.data = product;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PostMapping("/")
  public BaseResponse<HashMap<String, Boolean>> createProduct(@RequestBody CreateProductRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", productService.create(requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PutMapping("/{productId}")
  public BaseResponse<HashMap<String, Boolean>> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", productService.update(productId, requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @DeleteMapping("/{productId}")
  public BaseResponse<HashMap<String, Boolean>> deleteProduct(@PathVariable Long productId) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", productService.delete(productId));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }
}