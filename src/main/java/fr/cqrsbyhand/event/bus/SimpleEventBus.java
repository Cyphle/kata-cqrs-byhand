package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.handlers.EventHandler;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.utils.DateService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleEventBus implements EventBus {
  private static EventBus instance;
  private final List<EventBusSubscriber> subscribers;
  private final DateService dateService;

  private SimpleEventBus(DateService dateService) {
    this.dateService = dateService;
    subscribers = new ArrayList<>();
  }

  public static EventBus BUS() {
    if (instance == null)
      throw new IllegalStateException("Event bus has to be initialized before being used");
    return instance;
  }

  public static void initialize(DateService dateService) {
    instance = new SimpleEventBus(dateService);
    instance.getSubscribers().clear();
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
    event.setEventDate(dateService.now());
    subscribers.forEach(subscriber -> {
      if (subscriber.getEventClass().equals(event.getClass()))
        subscriber.apply(event);
    });
  }
}
