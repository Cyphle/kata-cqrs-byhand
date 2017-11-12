package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.events.AccountCreatedEvent;
import fr.cqrsbyhand.command.events.Event;

public interface EventBus {
  void apply(Event event);
}
