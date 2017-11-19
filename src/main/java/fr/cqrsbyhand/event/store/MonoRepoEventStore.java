package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;

import java.util.List;

public enum MonoRepoEventStore implements EventStore {
  STORE;

  @Override
  public List<Event> getAllEventsByType(EventType eventType) {
    /*
      TO STORE IN EVENT STORE REPOSITORY
     */
    throw new UnsupportedOperationException();
  }
}
