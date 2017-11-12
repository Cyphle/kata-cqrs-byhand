package fr.cqrsbyhand.domain.commandhandlers;

import fr.cqrsbyhand.command.commands.Command;

public abstract class CommandHandler {
  public abstract void handle(Command command);
}
