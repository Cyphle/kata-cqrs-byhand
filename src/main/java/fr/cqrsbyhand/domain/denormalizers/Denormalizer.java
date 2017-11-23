package fr.cqrsbyhand.domain.denormalizers;

import fr.cqrsbyhand.event.events.Event;

import java.util.List;
import java.util.Map;

public interface Denormalizer<T> {
  Map<String, T> project(List<Event> events);
}
