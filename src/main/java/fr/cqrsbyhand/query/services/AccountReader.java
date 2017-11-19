package fr.cqrsbyhand.query.services;

import fr.cqrsbyhand.query.models.AccountView;

public class AccountReader implements AccountQuery {
  @Override
  public AccountView getAccountByName(String accountName) {
    throw new UnsupportedOperationException();
  }
}
