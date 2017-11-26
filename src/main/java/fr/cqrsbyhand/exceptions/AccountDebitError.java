package fr.cqrsbyhand.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountDebitError implements CommandError {
  private final String reason;
  private final String accountName;

  public AccountDebitError(String reason, String accountName) {
    this.reason = reason;
    this.accountName = accountName;
  }
}
