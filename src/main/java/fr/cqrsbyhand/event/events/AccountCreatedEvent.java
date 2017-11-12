package fr.cqrsbyhand.event.events;

import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
public class AccountCreatedEvent implements Event {
  public AccountCreatedEvent(String accountId, String accountName, LocalDateTime eventDate) {

  }
}
