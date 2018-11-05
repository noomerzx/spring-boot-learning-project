package com.project.homework.response;

import lombok.Data;

@Data
public class BaseResponse<T> {
  public Integer status;
  public String code;
  public T data;

  // BaseResponse () {
  //   this.status = 200;
  //   this.code = "0";
  // }

  // BaseResponse (T data) {
  //   this.data = data;
  //   this.status = 200;
  //   this.code = "0";
  // }
}