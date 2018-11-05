package com.project.homework.interceptors;

import java.io.IOException;

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

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    try {
      System.out.println("Request coming...");
      String token = request.getHeader("Authorization");
      if (token == null) {
        throw new JWTVerificationException("Token missing.");
      }
      System.out.println("Token ==== " + token);
      Algorithm algorithm = Algorithm.HMAC256("secret");
      JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
      DecodedJWT jwt = verifier.verify(token);
      HttpSession session = request.getSession();
      String jwtBody = jwt.getSubject();
      Gson gson = new Gson();
      User user = gson.fromJson(jwtBody, User.class);
      session.setAttribute("userId", user.id);
      System.out.println("before return");
      return true;
    } catch (JWTVerificationException e) {
      System.out.println("Error in interceptor =====");
      response.sendError(200, "Unauthorization");
      return false;
    }
  }
}