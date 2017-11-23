package fr.cqrsbyhand.utils;

import java.time.LocalDateTime;
import java.time.Month;

public class Clock implements DateService {
  @Override
  public LocalDateTime now() {
    return LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0);
  }
}
