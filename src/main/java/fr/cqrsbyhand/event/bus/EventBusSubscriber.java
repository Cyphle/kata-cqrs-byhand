package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.handlers.EventHandler;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class EventBusSubscriber {
  private final Class<? extends Event> eventClass;
  private final EventHandler eventHandler;

  public EventBusSubscriber(Class<? extends Event> eventClass, EventHandler eventHandler) {
    this.eventClass = eventClass;
    this.eventHandler = eventHandler;
  }

  public Class<? extends Event> getEventClass() {
    return eventClass;
  }

  public void apply(Event event) {
    this.eventHandler.handle(event);
  }
}
