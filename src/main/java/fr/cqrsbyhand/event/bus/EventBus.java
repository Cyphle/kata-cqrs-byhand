package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.handlers.EventHandler;

import java.util.List;

public interface EventBus {
  List<EventBusSubscriber> getSubscribers();

  List<EventBusSubscriber> getSubscribersOf(Class<? extends Event> eventClass);

  void emptySubscribers();

  void apply(Event event);

  void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler);
}
