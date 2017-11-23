package fr.cqrsbyhand.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreationError implements CommandError {
  private String reason;
  private String accountName;

  public AccountCreationError(String reason, String accountName) {
    this.reason = reason;
    this.accountName = accountName;
  }
}
