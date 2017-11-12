package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.CommandResult;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.commandhandlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.commandhandlers.CommandHandler;

import java.util.Map;

public interface CommandBus {
  CommandResult send(Command command);

  void subscribe(Class<? extends Command> command, CommandHandler commandHandler);

  Map<Class<? extends Command>, CommandHandler> getSubscribers();
}
