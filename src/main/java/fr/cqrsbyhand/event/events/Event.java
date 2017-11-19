package fr.cqrsbyhand.event.events;

import java.time.LocalDateTime;

public interface Event {
  LocalDateTime getEventDate();
}
