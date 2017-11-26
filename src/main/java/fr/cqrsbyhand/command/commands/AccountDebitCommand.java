package fr.cqrsbyhand.command.commands;

public class AccountDebitCommand implements Command {
  private String accountId;
  private int amountToDebit;

  public AccountDebitCommand(String accountId, int amountToDebit) {
    this.accountId = accountId;
    this.amountToDebit = amountToDebit;
  }

  public String getAccountId() {
    return accountId;
  }

  public int getAmountToDebit() {
    return amountToDebit;
  }
}
