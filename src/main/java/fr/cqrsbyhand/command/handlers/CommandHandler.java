package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.store.EventStore;

public abstract class CommandHandler {
  final EventBus eventBus;
  final EventStore eventStore;

  CommandHandler(EventBus eventBus, EventStore eventStore) {
    this.eventBus = eventBus;
    this.eventStore = eventStore;
  }

  public abstract void handle(Command command) throws Exception;
}
