package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountDebitCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountDebitError;
import fr.cqrsbyhand.exceptions.CommandException;

import java.util.List;

public class AccountDebitCommandHandler extends CommandHandler {
  private Denormalizer<Account> denormalizer;

  public AccountDebitCommandHandler(EventBus eventBus, EventStore eventStore, Denormalizer<Account> denormalizer) {
    super(eventBus, eventStore);
    this.denormalizer = denormalizer;
  }

  @Override
  public void handle(Command command) throws Exception {
    List<Event> events = eventStore.getEventsOf(((AccountDebitCommand) command).getAccountId());

    if (events.isEmpty())
      throw new CommandException(new AccountDebitError("Account already does not exist", ((AccountDebitCommand) command).getAccountId()));

    Account account = denormalizer.project(events).get(((AccountDebitCommand) command).getAccountId());
    account.debit(((AccountDebitCommand) command).getAmountToDebit());
  }
}
