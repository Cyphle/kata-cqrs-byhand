package fr.cqrsbyhand.config;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.command.handlers.AccountCreditCommandHandler;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandler;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandlerForQuery;
import fr.cqrsbyhand.event.handlers.AccountCreditedEventHandler;
import fr.cqrsbyhand.event.handlers.AccountCreditedEventHandlerForQuery;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.mocks.MockAccountRepository;
import fr.cqrsbyhand.mocks.MockEventRepository;
import fr.cqrsbyhand.query.repositories.Bank;
import fr.cqrsbyhand.utils.AccountIdGenerator;
import fr.cqrsbyhand.utils.Clock;

public enum ApplicationConfig {
  CONFIG;

  private static Bank bank;

  public void initialize() {
    initializeEventStore();
    initializeEventBus();
    registerCommandHandlers();
    registerEventHandlers();
  }

  public void initializeEventStore() {
    MonoRepoEventStore.initialize(new MockEventRepository());
  }

  public void initializeEventBus() {
    SimpleEventBus.initialize(new Clock());
  }

  private void registerCommandHandlers() {
    SimpleCommandBus.BUS.subscribe(
            AccountCreationCommand.class,
            new AccountCreationCommandHandler(
                    SimpleEventBus.BUS(),
                    MonoRepoEventStore.STORE(),
                    new AccountDenormalizer(),
                    new AccountIdGenerator()
            ));
    SimpleCommandBus.BUS.subscribe(
            AccountCreditCommand.class,
            new AccountCreditCommandHandler(
                    SimpleEventBus.BUS(),
                    MonoRepoEventStore.STORE(),
                    new AccountDenormalizer()
            ));
  }

  private void registerEventHandlers() {
    bank = new MockAccountRepository();
    SimpleEventBus.BUS().subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandler(MonoRepoEventStore.STORE()));
    SimpleEventBus.BUS().subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandlerForQuery(bank));

    SimpleEventBus.BUS().subscribe(AccountCreditedEvent.class, new AccountCreditedEventHandler(MonoRepoEventStore.STORE()));
    SimpleEventBus.BUS().subscribe(AccountCreditedEvent.class, new AccountCreditedEventHandlerForQuery(bank, MonoRepoEventStore.STORE()));
  }

  public static EventStore getEventStore() {
    return MonoRepoEventStore.STORE();
  }

  public static Bank getBank() {
    return bank;
  }

  public static CommandBus getCommandBus() {
    return SimpleCommandBus.BUS;
  }
}
