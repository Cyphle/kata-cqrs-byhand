package fr.cqrsbyhand.exceptions;

public class AccountCreationError implements CommandError {
  private String reason;
  private String accountName;

  public AccountCreationError(String reason, String accountName) {
    this.reason = reason;
    this.accountName = accountName;
  }
}
