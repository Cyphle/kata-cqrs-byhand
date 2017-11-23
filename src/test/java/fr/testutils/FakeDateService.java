package fr.testutils;

import fr.cqrsbyhand.utils.DateService;

import java.time.LocalDateTime;
import java.time.Month;

public class FakeDateService implements DateService {
  @Override
  public LocalDateTime now() {
    return LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0);
  }
}
