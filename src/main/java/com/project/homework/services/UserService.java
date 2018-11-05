package com.project.homework.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import com.project.homework.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.project.homework.entities.TokenHistoryEntity;
import com.project.homework.entities.UserEntity;
import com.project.homework.repositories.TokenHistoryRepository;
import com.project.homework.repositories.UserRepository;
import com.project.homework.request.CreateUserRequest;
import com.project.homework.request.LoginUserRequest;
import com.project.homework.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  TokenHistoryRepository tokenRepository;

  @Value("${applicationSecret}")
  private String applicationSecret;

  @Value("${tokenTtl}")
  private Long tokenTtll;


  public Boolean InvokeToken (String token) {
    try {
      TokenHistoryEntity tokenE = tokenRepository.findByToken(token);
      tokenRepository.delete(tokenE);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String generateToken (LoginUserRequest loginData) {
    try {
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
      UserEntity u = userRepository.findByEmailAndPassword(loginData.email, loginData.password);
      Gson gson = new Gson();
      User user = new User();
      user.id = u.getId();
      user.email = u.getEmail();
      user.firstName = u.getFirstName();
      user.lastName = u.getLastName();
      Algorithm algorithm = Algorithm.HMAC256(applicationSecret);
      String jwtBody = gson.toJson(user);
      String token = JWT.create().withIssuer("auth0").withSubject(jwtBody).sign(algorithm);
      TokenHistoryEntity tokenE = new TokenHistoryEntity();
      tokenE.setToken(token);
      tokenE.setUserId(user.id);
      tokenE.setTtl(tokenTtll);
      tokenE.setCreateTime(currentTime);
      tokenRepository.save(tokenE);
      return token;
    } catch (JWTCreationException exception) {
      return "Failed";
    }
  }

  // public User getByLogin (LoginUserRequest loginData) {
  //   UserEntity u = userRepository.findByEmailAndPassword(loginData.email, loginData.password);
  //   User user = new User();
  //   user.id = u.getId();
  //   user.email = u.getEmail();
  //   user.firstName = u.getFirstName();
  //   user.lastName = u.getLastName();
  //   return user;
  // }

  public List<User> getAll () {
    List<UserEntity> users = userRepository.findAll();
    return users.stream().map(item -> {
      User user = new User();
      user.id = item.getId();
      user.firstName = item.getFirstName();
      user.lastName = item.getLastName();
      user.email = item.getEmail();
      return user;
    }).collect(Collectors.toList());
  }

  public User getById (Long userId) {
    UserEntity u = userRepository.findById(userId).get();
    User user = new User();
    user.id = u.getId();
    user.firstName = u.getFirstName();
    user.lastName = u.getLastName();
    user.email = u.getEmail();
    return user;
  }

  public boolean create (CreateUserRequest userData) {
    try {
      UserEntity user = new UserEntity();
      user.setFirstName(userData.firstName);
      user.setLastName(userData.lastName);
      user.setEmail(userData.email);
      user.setPassword(userData.password);
      userRepository.save(user);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean update (Long userId, UpdateUserRequest userData) {
    try {
      UserEntity user = userRepository.findById(userId).get();
      user.setFirstName(userData.firstName);
      user.setLastName(userData.lastName);
      userRepository.save(user);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean delete (Long userId) {
    try {
      userRepository.deleteById(userId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}