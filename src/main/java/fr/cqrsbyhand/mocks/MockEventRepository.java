package fr.cqrsbyhand.mocks;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class MockEventRepository implements EventRepository {
  private final List<Event> events;

  public MockEventRepository() {
    events = new ArrayList<>();
  }

  @Override
  public void save(Event event) {
    events.add(event);
  }

  @Override
  public List<Event> getEvents() {
    return events;
  }

  @Override
  public void clearEvents() {
    events.clear();
  }
}
