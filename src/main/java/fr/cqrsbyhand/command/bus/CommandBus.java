package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.command.handlers.CommandHandler;

import java.util.Map;

public interface CommandBus {
  void send(Command command) throws Exception;

  void subscribe(Class<? extends Command> command, CommandHandler commandHandler);

  Map<Class<? extends Command>, CommandHandler> getSubscribers();
}
