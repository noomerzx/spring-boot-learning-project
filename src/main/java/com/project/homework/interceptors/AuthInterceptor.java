package com.project.homework.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.project.homework.models.User;
import com.project.homework.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
  @Value("${applicationSecret}")
  private String applicationSecret;

  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    try {
      System.out.println("Request coming..., URI = '" + request.getRequestURI() + "'");
      System.out.println("Request method = " + request.getMethod());
      List<String> publicPaths = new ArrayList<String>();
      publicPaths.add("/error");
      publicPaths.add("/users/create");
      publicPaths.add("/users/login");
      publicPaths.add("/users/logout");
      publicPaths.add("/target/site/jacoco");
      String token = request.getHeader("Authorization");
      if (publicPaths.contains(request.getRequestURI())) {
        return true;
      } else if (token == null) {
        token = "invalid";
        // throw new JWTVerificationException("Token missing.");
      } else if (userService.checkValidToken(token) == false) {
        token = "invalid";
      }
      System.out.println("Token ==== " + token);
      Algorithm algorithm = Algorithm.HMAC256(applicationSecret);
      JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
      DecodedJWT jwt = verifier.verify(token);
      HttpSession session = request.getSession();
      String jwtBody = jwt.getSubject();
      Gson gson = new Gson();
      User user = gson.fromJson(jwtBody, User.class);
      session.setAttribute("userId", user.id);
      return true;
    } catch (JWTVerificationException e) {
      System.out.println("Error in interceptor =====" + e);
      response.sendError(400, "Unauthorization");
      return false;
    }
  }
}