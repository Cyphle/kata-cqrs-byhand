package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountCreationError;
import fr.cqrsbyhand.exceptions.AccountCreditError;
import fr.cqrsbyhand.exceptions.CommandException;
import fr.cqrsbyhand.utils.IdGenerator;

import java.util.List;

public class AccountCreditCommandHandler extends CommandHandler {
  private Denormalizer<Account> denormalizer;

  public AccountCreditCommandHandler(EventBus eventBus, EventStore eventStore, Denormalizer<Account> denormalizer) {
    super(eventBus, eventStore);
    this.denormalizer = denormalizer;
  }

  @Override
  public void handle(Command command) throws CommandException {
    List<Event> events = eventStore.getEventsOf(((AccountCreditCommand) command).getAccountId());

    if (events.isEmpty())
      throw new CommandException(new AccountCreditError("Account already does not exist", "abcuid"));

    eventBus.apply(new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, ((AccountCreditCommand) command).getAccountId(), ((AccountCreditCommand) command).getAmountToCredit()));
  }
}
