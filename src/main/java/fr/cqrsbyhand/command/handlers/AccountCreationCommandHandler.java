package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountCreationCommandHandler extends CommandHandler {
  @Override
  public void handle(Command command) {
    /*
    - Get all account aggregate (from event store)
    - Check name is not used (send result to denormalizer)
    - Send event account created to eventBus
     */
  }
}
