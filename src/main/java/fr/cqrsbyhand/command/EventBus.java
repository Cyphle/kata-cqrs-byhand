package fr.cqrsbyhand.command;

import fr.cqrsbyhand.command.events.AccountCreatedEvent;
import fr.cqrsbyhand.command.events.Event;

public interface EventBus {
  void apply(Event event);
}
