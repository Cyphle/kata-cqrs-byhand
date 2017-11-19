package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountCreationError;
import fr.cqrsbyhand.exceptions.CommandException;
import fr.cqrsbyhand.utils.IdGenerator;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode
public class AccountCreationCommandHandler extends CommandHandler {
  private Denormalizer<Account> denormalizer;
  private IdGenerator idGenerator;

  public AccountCreationCommandHandler(EventBus eventBus, EventStore eventStore, Denormalizer<Account> denormalizer, IdGenerator idGenerator) {
    super(eventBus, eventStore);
    this.denormalizer = denormalizer;
    this.idGenerator = idGenerator;
  }

  @Override
  public void handle(Command command) throws CommandException {
    Map<String, Account> accounts = getAllAccountsProjection();
    verifyAccountNotAlreadyExists(command, accounts);
    eventBus.apply(new AccountCreatedEvent(idGenerator.generateUuid(), ((AccountCreationCommand) command).getAccountName()));
  }

  private void verifyAccountNotAlreadyExists(Command command, Map<String, Account> accounts) throws CommandException {
    boolean doesAccountAlreadyExists = accounts.values()
            .stream()
            .anyMatch(account -> account.hasName(((AccountCreationCommand) command).getAccountName()));

    if (doesAccountAlreadyExists)
      throw new CommandException(new AccountCreationError("Account already exists", "My super account"));
  }

  private Map<String, Account> getAllAccountsProjection() {
    List<Event> events = this.eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION);
    return denormalizer.project(events);
  }
}
