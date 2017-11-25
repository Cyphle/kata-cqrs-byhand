package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;

import java.util.List;
import java.util.stream.Collectors;

public class MonoRepoEventStore implements EventStore {
  private static EventStore instance;
  private final EventRepository eventRepository;

  private MonoRepoEventStore(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public static EventStore STORE() {
    return instance;
  }

  public static void initialize(EventRepository eventRepository) {
    instance = new MonoRepoEventStore(eventRepository);
  }

  @Override
  public List<Event> getAllEventsByType(EventType eventType) {
    return eventRepository
            .getEvents()
            .stream()
            .filter(event -> event.getEventType().equals(eventType))
            .collect(Collectors.toList());
  }

  @Override
  public void save(Event event) {
    eventRepository.save(event);
  }

  @Override
  public List<Event> getAllEvents() {
    return eventRepository.getEvents();
  }

  @Override
  public List<Event> getEventsOf(String accountId) {
    return eventRepository
            .getEvents()
            .stream()
            .filter(event -> event.getAccountId().equals(accountId))
            .collect(Collectors.toList());
  }

  @Override
  public void clearEvents() {
    eventRepository.clearEvents();
  }
}
