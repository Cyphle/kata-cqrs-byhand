package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.Event;

import java.util.List;

public interface EventRepository {
  void save(Event event);

  List<Event> getEvents();

  void clearEvents();
}
