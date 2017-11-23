package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventStore;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class AccountCreatedEventHandler implements EventHandler {
  private EventStore eventStore;

  public AccountCreatedEventHandler(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public void handle(Event event) {
    List<Event> events = eventStore.getEventsOf(event.getAccountId());
    /*
      Save in query database
      -> get denormalizer
      -> get events
      -> update
      -> save
     */
    throw new UnsupportedOperationException();
  }
}
