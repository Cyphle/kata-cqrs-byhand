package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventStore;

public class AccountDebitedEventHandler implements EventHandler {
  private EventStore store;

  public AccountDebitedEventHandler(EventStore store) {
    this.store = store;
  }

  @Override
  public void handle(Event event) {
    store.save(event);
  }
}
