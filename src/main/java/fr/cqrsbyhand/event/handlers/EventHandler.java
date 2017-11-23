package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.Event;

public interface EventHandler {
  void handle(Event event);
}
