package fr.cqrsbyhand.domain.denormalizers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.event.events.Event;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDenormalizer implements Denormalizer<Account> {
  @Override
  public List<Account> project(List<Event> events) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Event> sortEvents(List<Event> events) {
    return events.stream()
            .sorted(Comparator.comparing(Event::getEventDate))
            .collect(Collectors.toList());
  }
}
