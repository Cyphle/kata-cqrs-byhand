package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.Event;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreatedEventHandlerForQuery implements EventHandler {
  @Override
  public void handle(Event event) {
    throw new UnsupportedOperationException();
  }
}
