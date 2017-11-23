package fr.cqrsbyhand.query.repositories;

import fr.cqrsbyhand.domain.aggregates.Account;

public interface Bank {
  void createAccount(Account account);
}
