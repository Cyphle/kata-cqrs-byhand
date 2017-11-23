package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.query.repositories.Bank;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;

@EqualsAndHashCode
public class AccountCreatedEventHandler implements EventHandler {
  private AccountDenormalizer denormalizer;
  private Bank bank;

  public AccountCreatedEventHandler(Bank bank) {
    this.bank = bank;
    denormalizer = new AccountDenormalizer();
  }

  @Override
  public void handle(Event event) {
    Map<String, Account> account = denormalizer.project(Collections.singletonList(event));
    bank.createAccount(account.get(event.getAccountId()));
  }
}
