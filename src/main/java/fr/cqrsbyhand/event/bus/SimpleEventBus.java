package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.handlers.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum SimpleEventBus implements EventBus {
  BUS;

  private final List<EventBusSubscriber> subscribers;

  SimpleEventBus() {
    subscribers = new ArrayList<>();
  }

  @Override
  public void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler) {
    subscribers.add(new EventBusSubscriber(eventClass, eventHandler));
  }

  @Override
  public List<EventBusSubscriber> getSubscribers() {
    return subscribers;
  }

  @Override
  public List<EventBusSubscriber> getSubscribersOf(Class<? extends Event> eventClass) {
    return subscribers.stream()
            .filter(subscriber -> subscriber.getEventClass().equals(eventClass))
            .collect(Collectors.toList());
  }

  @Override
  public void emptySubscribers() {
    subscribers.clear();
  }

  @Override
  public void apply(Event event) {
    /*
    Update event with current date
     */
    throw new UnsupportedOperationException();
  }
}
