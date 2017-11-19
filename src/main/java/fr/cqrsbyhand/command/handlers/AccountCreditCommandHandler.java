package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;

public class AccountCreditCommandHandler extends CommandHandler {
  private Denormalizer<Account> denormalizer;

  public AccountCreditCommandHandler(EventBus eventBus, EventStore eventStore, Denormalizer<Account> denormalizer) {
    super(eventBus, eventStore);
  }

  @Override
  public void handle(Command command) {

  }
}
