package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.store.EventStore;

public class AccountCreditCommandHandler extends CommandHandler {
  public AccountCreditCommandHandler(EventBus eventBus, EventStore eventStore) {
    super(eventBus, eventStore);
  }

  @Override
  public void handle(Command command) {
    throw new UnsupportedOperationException();
  }
}
