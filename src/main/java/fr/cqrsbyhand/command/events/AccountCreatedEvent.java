package fr.cqrsbyhand.command.events;

import java.time.LocalDateTime;

public class AccountCreatedEvent implements Event {
  public AccountCreatedEvent(String accountId, String accountName, LocalDateTime eventDate) {

  }
}
