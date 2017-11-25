package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;

import java.util.List;

public class AccountCreditedEventHandlerForQuery implements EventHandler {
  private final Bank bank;
  private EventStore eventStore;
  private final Denormalizer<Account> denormalizer;

  public AccountCreditedEventHandlerForQuery(Bank bank, EventStore eventStore) {
    this.bank = bank;
    this.eventStore = eventStore;
    denormalizer = new AccountDenormalizer();
  }

  @Override
  public void handle(Event event) {
    List<Event> events = eventStore.getEventsOf(event.getAccountId());
    Account account = denormalizer.project(events).get(event.getAccountId());
//    account.credit(event.getAmount());
    AccountView accountView = AccountView.fromAccountAggregate(account);
    bank.updateAccount(accountView);
  }
}
