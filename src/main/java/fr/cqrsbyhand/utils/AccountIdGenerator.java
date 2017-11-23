package fr.cqrsbyhand.utils;

import java.util.UUID;

public class AccountIdGenerator implements IdGenerator {
  @Override
  public String generateUuid() {
//    return UUID.randomUUID().toString();
    return "abcuid";
  }
}
