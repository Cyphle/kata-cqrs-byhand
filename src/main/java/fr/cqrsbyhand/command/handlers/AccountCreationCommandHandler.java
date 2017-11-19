package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.exceptions.AccountCreationError;
import fr.cqrsbyhand.exceptions.CommandException;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
public class AccountCreationCommandHandler extends CommandHandler {
  private Denormalizer<Account> denormalizer;

  public AccountCreationCommandHandler(EventBus eventBus, EventStore eventStore, Denormalizer<Account> denormalizer) {
    super(eventBus, eventStore);
    this.denormalizer = denormalizer;
  }

  @Override
  public void handle(Command command) throws CommandException {
    Map<String, Account> accounts = getAllAccountsProjection();

    boolean doesAccountAlreadyExists = accounts.values()
            .stream()
            .anyMatch(account -> account.hasName(((AccountCreationCommand) command).getAccountName()));

    if (doesAccountAlreadyExists) {
      throw new CommandException(new AccountCreationError("Account already exists", "My super account"));
    }

//    if (accounts.containsKey(command.getAccountId())) {
//
//    } else {
//
//    }
    /*
    - Get all account aggregate (from event store)
    - Check name is not used (send result to denormalizer)
    - Send event account created to eventBus
     */
  }

  private Map<String, Account> getAllAccountsProjection() {
    List<Event> events = this.eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION);
    return denormalizer.project(events);
  }
}
