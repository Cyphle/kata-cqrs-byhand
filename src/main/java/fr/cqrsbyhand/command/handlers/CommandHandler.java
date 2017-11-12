package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;

public abstract class CommandHandler {
  public CommandHandler() {
//    eventBus = SimpleEventBus.BUS;
//    eventStore = MonoRepoEventStore.STORE;
  }

  public abstract void handle(Command command);
}
