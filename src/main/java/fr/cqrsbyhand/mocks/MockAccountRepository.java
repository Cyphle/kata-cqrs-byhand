package fr.cqrsbyhand.mocks;

import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class MockAccountRepository implements Bank {
  private List<AccountView> accounts;

  public MockAccountRepository() {
    accounts = new ArrayList<>();
  }

  @Override
  public void createAccount(AccountView account) {
    accounts.add(account);
  }

  @Override
  public AccountView findByName(String accountName) {
    return accounts.stream()
            .filter(account -> account.getName().equals(accountName))
            .findAny()
            .orElse(new AccountView());
  }
}
