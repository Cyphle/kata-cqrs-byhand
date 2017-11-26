package fr.cqrsbyhand.domain.aggregates;

import fr.cqrsbyhand.exceptions.AccountDebitError;
import fr.cqrsbyhand.exceptions.CommandException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class Account {
  private final String accountId;
  private final LocalDateTime creationDate;
  private LocalDateTime lastUpdateDate;
  private final String accountName;
  private int balance;

  public Account(String accountId, LocalDateTime creationDate, LocalDateTime lastUpdateDate, String accountName, int balance) {
    this.accountId = accountId;
    this.creationDate = creationDate;
    this.lastUpdateDate = lastUpdateDate;
    this.accountName = accountName;
    this.balance = balance;
  }

  public Account credit(int amountToCredit) {
    return new Account(accountId, creationDate, lastUpdateDate, accountName, balance += amountToCredit);
  }

  public Account debit(int amountToDebit) throws CommandException {
    if (balance - amountToDebit < 0)
      throw new CommandException(new AccountDebitError("You cannot have negative balance", accountName));
    return new Account(accountId, creationDate, lastUpdateDate, accountName, balance -= amountToDebit);
  }

  public void updateLastUpdateDate(LocalDateTime newLastUpdateDate) {
    lastUpdateDate = newLastUpdateDate;
  }

  public boolean hasName(String nameToCompare) {
    return accountName.equals(nameToCompare);
  }

  public String getAccountName() {
    return accountName;
  }

  public String getAccountId() {
    return accountId;
  }

  public int getBalance() {
    return balance;
  }
}
