package fr.cqrsbyhand.event.events;

import java.time.LocalDateTime;

public interface Event {
  String getAccountId();

  int getAmount();

  LocalDateTime getEventDate();
}
