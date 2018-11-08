package com.project.homework.utils;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
  public Timestamp getCurrentTimestamp () {
    return new Timestamp(Calendar.getInstance().getTime().getTime());
  }
}