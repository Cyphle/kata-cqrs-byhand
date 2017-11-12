package fr.cqrsbyhand.command;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;

public interface CommandBus {
  CommandResult send(Command command);
}
