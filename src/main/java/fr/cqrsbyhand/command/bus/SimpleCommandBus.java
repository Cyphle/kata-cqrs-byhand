package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.command.handlers.CommandHandler;

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
  public void send(Command command) {
    CommandHandler handler = subscribers.get(command.getClass());
    handler.handle(command);
  }
}
