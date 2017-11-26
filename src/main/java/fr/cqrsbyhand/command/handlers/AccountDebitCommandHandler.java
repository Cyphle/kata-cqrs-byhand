package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.command.commands.AccountDebitCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.AccountDebitedEvent;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
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

    verifyAccountExists((AccountDebitCommand) command, events);
    verifyTransactionIsAllowed((AccountDebitCommand) command, events);

    eventBus.apply(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, ((AccountDebitCommand) command).getAccountId(), ((AccountDebitCommand) command).getAmountToDebit()));
  }

  private void verifyAccountExists(AccountDebitCommand command, List<Event> events) throws CommandException {
    if (events.isEmpty())
      throw new CommandException(new AccountDebitError("Account already does not exist", command.getAccountId()));
  }

  private void verifyTransactionIsAllowed(AccountDebitCommand command, List<Event> events) throws CommandException {
    Account account = denormalizer.project(events).get(command.getAccountId());
    account.debit(command.getAmountToDebit());
  }
}
