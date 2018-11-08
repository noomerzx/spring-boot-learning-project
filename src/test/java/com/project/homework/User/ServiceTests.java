package com.project.homework.User;

import com.project.homework.services.UserService;
import com.project.homework.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.project.homework.db1.entities.TokenHistoryEntity;
import com.project.homework.db1.entities.UserEntity;
import com.project.homework.models.User;
import com.project.homework.db1.repositories.TokenHistoryRepository;
import com.project.homework.db1.repositories.UserRepository;
import com.project.homework.request.CreateUserRequest;
import com.project.homework.request.LoginUserRequest;
import com.project.homework.request.UpdateUserRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

  @Mock
  UserRepository userRepositoryMock;

  @Mock
  TokenHistoryRepository tokenRepositoryMock;

  @Mock
  DateUtils dateUtil;

	@InjectMocks
  UserService userService;
  
	@Test
	public void contextLoads() {
	}

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(userService, "applicationSecret", "KOOD7FppxUNhoa0ncVng-WVNYF6qe4pdfkVNXZbzW");
    ReflectionTestUtils.setField(userService, "tokenTtl", 604800000l);
  }

  @Test
  public void testCheckValidTokenIsValid() {
    TokenHistoryEntity tokenE = new TokenHistoryEntity();
    when(tokenRepositoryMock.findByToken(any())).thenReturn(tokenE);
    assertEquals(userService.checkValidToken(any()), true);
  }

  @Test
  public void testCheckValidTokenIsInvalid() {
    when(tokenRepositoryMock.findByToken(any())).thenReturn(null);
    assertEquals(userService.checkValidToken(any()), false);
  }

  @Test
  public void testGenerateToken() {
    Gson gson = new Gson();
    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
    LoginUserRequest loginData = new LoginUserRequest();
    loginData.email = "xxx@gmail.com";
    loginData.password = "password1234";
    UserEntity userE = new UserEntity();
    userE.setId(1l);
    userE.setEmail("xxx@gmail.com");
    userE.setFirstName("first");
    userE.setLastName("last");
    TokenHistoryEntity tokenE = new TokenHistoryEntity();
    when(userRepositoryMock.findByEmailAndPassword(any(), any())).thenReturn(userE);
    when(tokenRepositoryMock.save(any())).thenReturn(tokenE);
    when(dateUtil.getCurrentTimestamp()).thenReturn(currentTime);
    HashMap<String, String> body = new HashMap<String, String>();
    body.put("id", userE.getId().toString());
    body.put("email", userE.getEmail());
    body.put("firstName", userE.getFirstName());
    body.put("lastName", userE.getLastName());
    body.put("timestamp", currentTime.toString());
    Algorithm algorithm = Algorithm.HMAC256("KOOD7FppxUNhoa0ncVng-WVNYF6qe4pdfkVNXZbzW");
    String jwtBody = gson.toJson(body);
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
    UserEntity userMock = new UserEntity();
    userData.firstName = "Wisarut";
    userData.firstName = "Phuv";
    userData.email = "xxxx@gmail.com";
    userData.password = "password";
    when(userRepositoryMock.save(any())).thenReturn(userMock);
    Boolean result = userService.create(userData);
    assertEquals(result, true);
  }

  @Test
  public void testUpdate() {
    UpdateUserRequest userData = new UpdateUserRequest();
    UserEntity userMock = new UserEntity();
    when(userRepositoryMock.findById(any())).thenReturn(Optional.of(userMock));
    userData.firstName = "Wisarut Update";
    userData.lastName = "Phuv Update";
    when(userRepositoryMock.save(any())).thenReturn(userMock);
    Boolean result = userService.update(1l, userData);
    assertEquals(result, true);
  }

  @Test
  public void testDelete() {
    Boolean result = userService.delete(1l);
    doNothing().when(userRepositoryMock).deleteById(any());
    assertEquals(result, true);
  }
}
