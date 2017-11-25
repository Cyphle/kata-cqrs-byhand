package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;

@EqualsAndHashCode
public class AccountCreatedEventHandlerForQuery implements EventHandler {
  private final Denormalizer denormalizer;
  private final Bank bank;

  public AccountCreatedEventHandlerForQuery(Bank bank) {
    this.bank = bank;
    denormalizer = new AccountDenormalizer();
  }

  @Override
  public void handle(Event event) {
    Map<String, Account> account = denormalizer.project(Collections.singletonList(event));
    bank.createAccount(AccountView.fromAccountAggregate(account.get(event.getAccountId())));
  }
}
