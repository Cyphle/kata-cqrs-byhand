package fr.cqrsbyhand.mocks;

import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
public class MockAccountRepository implements Bank {
  private final Map<String, AccountView> accounts;

  public MockAccountRepository() {
    accounts = new HashMap<>();
  }

  @Override
  public void createAccount(AccountView account) {
    accounts.put(account.getName(), account);
  }

  @Override
  public AccountView findByName(String accountName) {
    return accounts.get(accountName);
  }

  @Override
  public void updateAccount(AccountView account) {
    accounts.remove(account.getName());
    accounts.put(account.getName(), account);
  }

  @Override
  public void clear() {
    accounts.clear();
  }
}
