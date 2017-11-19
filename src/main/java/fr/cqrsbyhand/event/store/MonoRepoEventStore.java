package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum MonoRepoEventStore implements EventStore {
  STORE;

  private List<Event> events;

  MonoRepoEventStore() {
    this.events = new ArrayList<>();
  }

  @Override
  public List<Event> getAllEventsByType(EventType eventType) {
    return events.stream()
            .filter(event -> event.getEventType().equals(eventType))
            .collect(Collectors.toList());
  }

  @Override
  public void save(Event event) {
    events.add(event);
  }

  public List<Event> getAllEvents() {
    return events;
  }

  public void clearEvents() {
    events.clear();
  }
}
