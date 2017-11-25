package fr.cqrsbyhand.command.commands;

public class AccountCreditCommand implements Command {
  private String accountId;
  private int amountToCredit;

  public AccountCreditCommand(String accountId, int amountToCredit) {
    this.accountId = accountId;
    this.amountToCredit = amountToCredit;
  }

  public String getAccountId() {
    return accountId;
  }
}
