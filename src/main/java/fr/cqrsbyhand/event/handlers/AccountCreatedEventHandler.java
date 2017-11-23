package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventStore;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreatedEventHandler implements EventHandler {
  private EventStore eventStore;

  public AccountCreatedEventHandler(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public void handle(Event event) {
    eventStore.save(event);
  }
}
