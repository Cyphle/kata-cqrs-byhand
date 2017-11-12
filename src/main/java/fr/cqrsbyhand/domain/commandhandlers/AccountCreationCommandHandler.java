package fr.cqrsbyhand.domain.commandhandlers;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreationCommandHandler extends CommandHandler {
  public void handle(AccountCreationCommand command) {

  }
}
