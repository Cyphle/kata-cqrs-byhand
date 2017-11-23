package fr.cqrsbyhand.command.commands;

public class AccountCreationCommand implements Command {
  private final String accountName;

  public AccountCreationCommand(String accountName) {
    this.accountName = accountName;
  }

  public String getAccountName() {
    return accountName;
  }
}
