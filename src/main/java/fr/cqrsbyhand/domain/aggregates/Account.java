package fr.cqrsbyhand.domain.aggregates;

import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
public class Account {
  private String accountId;
  private LocalDateTime creationDate;
  private LocalDateTime lastUpdateDate;
  private int balance;

  public Account(String accountId, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int balance) {
    this.accountId = accountId;
    this.creationDate = creationDate;
    this.lastUpdateDate = lastUpdateDate;
    this.balance = balance;
  }

  public Account credit(int amountToCredit) {
    return new Account(accountId, creationDate, lastUpdateDate, balance += amountToCredit);
  }

  public Account debit(int amountToDebit) {
    return new Account(accountId, creationDate, lastUpdateDate, balance -= amountToDebit);
  }

  public void updateLastUpdateDate(LocalDateTime newLastUpdateDate) {
    lastUpdateDate = newLastUpdateDate;
  }
}
