package com.project.homework.controllers;

import java.util.HashMap;
import java.util.List;
import com.project.homework.response.*;
import com.project.homework.models.User;
import com.project.homework.request.CreateUserRequest;
import com.project.homework.request.LoginUserRequest;
import com.project.homework.request.UpdateUserRequest;
import com.project.homework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public BaseResponse<HashMap<String, String>> getToken(@RequestBody LoginUserRequest requestData) {
    BaseResponse<HashMap<String, String>> response = new BaseResponse<HashMap<String, String>>();
    HashMap<String, String> result = new HashMap<String, String>();
    String data = userService.generateToken(requestData);
    if (data == "Failed") {
      result.put("success", "false");
    } else {
      result.put("success", "true");
      result.put("token", data);
    }
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PostMapping("/logout")
  public BaseResponse<HashMap<String, Boolean>>  getToken(@RequestHeader("Authorization") String token) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", userService.InvokeToken(token));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @GetMapping("/")
  public BaseResponse<List<User>> getUsers() {
    List<User> users = userService.getAll();
    BaseResponse<List<User>> response = new BaseResponse<List<User>>();
    response.data = users;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @GetMapping("/{userId}")
  public BaseResponse<User> getUser(@PathVariable Long userId) {
    User user = userService.getById(userId);
    BaseResponse<User> response = new BaseResponse<User>();
    response.data = user;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PostMapping("/")
  public BaseResponse<HashMap<String, Boolean>> createUser(@RequestBody CreateUserRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", userService.create(requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @PutMapping("/{userId}")
  public BaseResponse<HashMap<String, Boolean>> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest requestData) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", userService.update(userId, requestData));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }

  @DeleteMapping("/{userId}")
  public BaseResponse<HashMap<String, Boolean>> deleteUser(@PathVariable Long userId) {
    BaseResponse<HashMap<String, Boolean>> response = new BaseResponse<HashMap<String, Boolean>>();
    HashMap<String, Boolean> result = new HashMap<String, Boolean>();
    result.put("success", userService.delete(userId));
    response.data = result;
    response.code = "0";
    response.status = 200;
    return response;
  }
}