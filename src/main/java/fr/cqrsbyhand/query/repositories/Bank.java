package fr.cqrsbyhand.query.repositories;

import fr.cqrsbyhand.query.models.AccountView;

public interface Bank {
  void createAccount(AccountView account);

  AccountView findByName(String accountName);

  void updateAccount(AccountView account);

  void clear();
}
