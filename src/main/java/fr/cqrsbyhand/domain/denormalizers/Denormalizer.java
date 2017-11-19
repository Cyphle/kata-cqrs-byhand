package fr.cqrsbyhand.domain.denormalizers;

import fr.cqrsbyhand.event.events.Event;

import java.util.List;

public interface Denormalizer<T> {
  List<T> project(List<Event> events);

  List<Event> sortEvents(List<Event> events);
}
