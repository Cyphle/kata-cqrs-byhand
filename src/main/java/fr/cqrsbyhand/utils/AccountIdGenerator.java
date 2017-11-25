package fr.cqrsbyhand.utils;

public class AccountIdGenerator implements IdGenerator {
  @Override
  public String generateUuid() {
//    return UUID.randomUUID().toString();
    return "abcuid";
  }
}
