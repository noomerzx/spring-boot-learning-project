package com.project.homework.services;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;

import com.project.homework.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.project.homework.db1.entities.TokenHistoryEntity;
import com.project.homework.db1.entities.UserEntity;
import com.project.homework.db1.repositories.TokenHistoryRepository;
import com.project.homework.db1.repositories.UserRepository;
import com.project.homework.request.CreateUserRequest;
import com.project.homework.request.LoginUserRequest;
import com.project.homework.request.UpdateUserRequest;
import com.project.homework.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenHistoryRepository tokenRepository;

  @Autowired
  private DateUtils dateUtil;

  @Autowired
  @Qualifier("transactionManager")
  private PlatformTransactionManager db1TransactionManager;

  @Value("${applicationSecret}")
  private String applicationSecret;

  @Value("${tokenTtl}")
  private Long tokenTtl;

  public Boolean checkValidToken (String token) {
    TokenHistoryEntity tokenE = tokenRepository.findByToken(token);
    if (tokenE == null) {
      return false;
    } else {
      return true;
    }
  }

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
      Timestamp currentTime = dateUtil.getCurrentTimestamp();
      UserEntity u = userRepository.findByEmailAndPassword(loginData.email, loginData.password);
      if (u == null) {
        throw new JWTCreationException("User not found.", null);
      }
      Gson gson = new Gson();
      HashMap<String, String> body = new HashMap<String, String>();
      body.put("id", u.getId().toString());
      body.put("email", u.getEmail());
      body.put("firstName", u.getFirstName());
      body.put("lastName", u.getLastName());
      body.put("timestamp", currentTime.toString());
      // User user = new User();
      // user.id = u.getId();
      // user.email = u.getEmail();
      // user.firstName = u.getFirstName();
      // user.lastName = u.getLastName();
      System.out.println(applicationSecret);
      Algorithm algorithm = Algorithm.HMAC256(applicationSecret);
      String jwtBody = gson.toJson(body);
      String token = JWT.create().withIssuer("auth0").withSubject(jwtBody).sign(algorithm);
      TokenHistoryEntity tokenE = new TokenHistoryEntity();
      tokenE.setToken(token);
      tokenE.setUserId(u.getId());
      tokenE.setTtl(tokenTtl);
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

  @Transactional
  public boolean create (CreateUserRequest userData) throws TransactionalException {
    // DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    // def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    // def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
    // def.setReadOnly(false);
    // TransactionStatus transaction = db1TransactionManager.getTransaction(def);
    try {
      UserEntity user = new UserEntity();
      user.setFirstName(userData.firstName);
      user.setLastName(userData.lastName);
      user.setEmail(userData.email);
      user.setPassword(userData.password);
      userRepository.save(user);
      throw new TransactionalException(applicationSecret, null);
      // db1TransactionManager.rollback(transaction);
      // return true;
    } catch (Exception e) {
      System.out.println(e);
      throw new TransactionalException(applicationSecret, null);
      // db1TransactionManager.rollback(transaction);
      // return false;
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