package fr.cqrsbyhand.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreditError implements CommandError {
  private final String reason;
  private final String accountName;

  public AccountCreditError(String reason, String accountName) {
    this.reason = reason;
    this.accountName = accountName;
  }
}
