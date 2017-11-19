package fr.cqrsbyhand.event.events;

import java.time.LocalDateTime;

public interface Event {
  String getAccountId();

  String getAccountName();

  int getAmount();

  LocalDateTime getEventDate();
}
