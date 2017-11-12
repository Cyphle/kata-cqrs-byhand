package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.CommandResult;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.commandhandlers.CommandHandler;

import java.util.HashMap;
import java.util.Map;

public enum SimpleCommandBus implements CommandBus {
  BUS;

  private Map<Class<? extends Command>, CommandHandler> subscribers;

  SimpleCommandBus() {
    this.subscribers = new HashMap<>();
  }

  @Override
  public void subscribe(Class<? extends Command> command, CommandHandler commandHandler) {
    subscribers.put(command, commandHandler);
  }

  @Override
  public Map<Class<? extends Command>, CommandHandler> getSubscribers() {
    return subscribers;
  }

  @Override
  public CommandResult send(Command command) {
    return null;
  }
}
