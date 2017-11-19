package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;

import java.util.List;

public interface EventStore {
  List<Event> getAllEventsByType(EventType eventType);
}
