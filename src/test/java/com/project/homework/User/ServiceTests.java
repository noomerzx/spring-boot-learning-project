package com.project.homework.User;

import com.project.homework.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.project.homework.entities.TokenHistoryEntity;
import com.project.homework.entities.UserEntity;
import com.project.homework.models.User;
import com.project.homework.repositories.TokenHistoryRepository;
import com.project.homework.repositories.UserRepository;
import com.project.homework.request.CreateUserRequest;
import com.project.homework.request.LoginUserRequest;
import com.project.homework.request.UpdateUserRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {
  @Value("${applicationSecret}")
  private String applicationSecret;

  @Mock
  UserRepository userRepositoryMock;

  @Mock
  TokenHistoryRepository tokenRepositoryMock;

	@InjectMocks
  UserService userService;
  
	@Test
	public void contextLoads() {
	}

  // @Test
  // public void testGeByLogIn() {
  //   UserEntity userMock = new UserEntity();
  //   LoginUserRequest loginData = new LoginUserRequest();
  //   userMock.setId(1l);
  //   loginData.email = "xxxx@gmail.com";
  //   loginData.password = "password";
  //   when(userRepositoryMock.findByEmailAndPassword(loginData.email, loginData.password)).thenReturn(userMock);
  //   User user = userService.getByLogin(loginData);
  //   assertEquals(user.id, userMock.getId());
  // }

  @Test
  public void testGenerateToken() {
    Gson gson = new Gson();
    LoginUserRequest loginData = new LoginUserRequest();
    loginData.email = "xxx@gmail.com";
    loginData.password = "password1234";
    UserEntity userE = new UserEntity();
    userE.setId(1l);
    userE.setEmail("xxx@gmail.com");
    userE.setFirstName("first");
    userE.setLastName("last");
    when(userRepositoryMock.findByEmailAndPassword(any(), any())).thenReturn(userE);
    when(tokenRepositoryMock.save(any())).thenReturn(true);
    User user = new User();
    user.id = userE.getId();
    user.email = userE.getEmail();
    user.firstName = userE.getFirstName();
    user.lastName = userE.getLastName();
    Algorithm algorithm = Algorithm.HMAC256(applicationSecret);
    String jwtBody = gson.toJson(user);
    String token = JWT.create().withIssuer("auth0").withSubject(jwtBody).sign(algorithm);
    assertEquals(userService.generateToken(loginData), token);
  }

  @Test
  public void testInvokeToken() {
    String token = "1234abcd";
    TokenHistoryEntity tokenE = new TokenHistoryEntity();
    when(tokenRepositoryMock.findByToken(any())).thenReturn(tokenE);
    doNothing().when(tokenRepositoryMock).delete(any());
    assertEquals(userService.InvokeToken(token), true);
  }

  @Test
  public void testGetAll() {
    List<UserEntity> mockList = new ArrayList<>();
    when(userRepositoryMock.findAll()).thenReturn(mockList);
    List<User> result = userService.getAll();
		assertEquals(mockList.size(), result.size());
  }

  @Test
  public void testGetById() {
    UserEntity userE = new UserEntity();
    userE.setId(1l);
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userE));
    User result = userService.getById(any());
    assertEquals(result.id, userE.getId());
  }

  @Test
  public void testCreate() {
    CreateUserRequest userData = new CreateUserRequest();
    userData.firstName = "Wisarut";
    userData.firstName = "Phuv";
    userData.email = "xxxx@gmail.com";
    userData.password = "password";
    Boolean result = userService.create(userData);
    assertEquals(result, true);
  }

  @Test
  public void testUpdate() {
    UpdateUserRequest productData = new UpdateUserRequest();
    UserEntity productMock = new UserEntity();
    when(userRepositoryMock.findById(any()).get()).thenReturn(productMock);
    productData.firstName = "Wisarut Update";
    productData.lastName = "Phuv Update";
    Boolean result = userService.update(1l, productData);
    assertEquals(result, true);
  }

  @Test
  public void testDelete() {
    Boolean result = userService.delete(1l);
    assertEquals(result, true);
  }
}
