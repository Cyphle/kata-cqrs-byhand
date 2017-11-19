package fr.cqrsbyhand.domain.aggregates;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class Account {
  private String accountId;
  private LocalDateTime creationDate;
  private LocalDateTime lastUpdateDate;
  private String accountName;
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

  public Account debit(int amountToDebit) {
    return new Account(accountId, creationDate, lastUpdateDate, accountName, balance -= amountToDebit);
  }

  public void updateLastUpdateDate(LocalDateTime newLastUpdateDate) {
    lastUpdateDate = newLastUpdateDate;
  }

  public boolean hasName(String nameToCompare) {
    return accountName.equals(nameToCompare);
  }
}
