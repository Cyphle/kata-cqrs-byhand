package fr.cqrsbyhand.domain.denormalizers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.AccountDebitedEvent;
import fr.cqrsbyhand.event.events.Event;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class AccountDenormalizer implements Denormalizer<Account> {
  @Override
  public Map<String, Account> project(List<Event> events) {
    List<Event> sortedEvents = this.sortEvents(events);

    Map<String, Account> accounts = new HashMap<>();

    sortedEvents.forEach(event -> {
      if (accounts.containsKey(event.getAccountId())) {
        Transaction.applyOperation(event, accounts.get(event.getAccountId()));
        accounts.get(event.getAccountId()).updateLastUpdateDate(event.getEventDate());
      } else {
        accounts.put(event.getAccountId(), new Account(event.getAccountId(), event.getEventDate(), event.getEventDate(), 0));
      }
    });

    return accounts;
  }

  List<Event> sortEvents(List<Event> events) {
    return events.stream()
            .sorted(Comparator.comparing(Event::getEventDate))
            .collect(Collectors.toList());
  }

  private enum Transaction {
    CREDIT(AccountCreditedEvent.class, Account::credit),
    DEBIT(AccountDebitedEvent.class, Account::debit);

    public final Class EventClass;
    public final BiFunction<Account, Integer, Account> operation;

    Transaction(Class eventClass, BiFunction<Account, Integer, Account> operation) {
      EventClass = eventClass;
      this.operation = operation;
    }

    public static Account applyOperation(Event event, Account account) {
      return Arrays.stream(Transaction.values())
              .filter(operation -> operation.EventClass.equals(event.getClass()))
              .findAny()
              .map(operation -> operation.operation.apply(account, event.getAmount()))
              .orElse(account);
    }
  }
}
