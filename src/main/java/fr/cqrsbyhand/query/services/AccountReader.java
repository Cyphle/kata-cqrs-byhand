package fr.cqrsbyhand.query.services;

import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;

public class AccountReader implements AccountQuery {
  private final Bank bank;

  public AccountReader(Bank bank) {
    this.bank = bank;
  }

  @Override
  public AccountView getAccountByName(String accountName) {
    return bank.findByName(accountName);
  }
}
