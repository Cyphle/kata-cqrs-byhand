package fr.cqrsbyhand.mocks;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.query.repositories.Bank;

public class MockAccountRepository implements Bank {
  @Override
  public void createAccount(Account account) {
    throw new UnsupportedOperationException();
  }
}
