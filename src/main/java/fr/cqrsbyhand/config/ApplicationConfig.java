package fr.cqrsbyhand.config;

import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandler;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandlerForQuery;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.mocks.MockAccountRepository;
import fr.cqrsbyhand.mocks.MockEventRepository;
import fr.cqrsbyhand.query.repositories.Bank;
import fr.cqrsbyhand.utils.AccountIdGenerator;
import fr.cqrsbyhand.utils.Clock;

public enum ApplicationConfig {
  CONFIG;

  private static Bank bank;

  public static Bank getBank() {
    return bank;
  }

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
    SimpleEventBus.initialize(MonoRepoEventStore.STORE(), new Clock());
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
  }

  private void registerEventHandlers() {
    bank = new MockAccountRepository();
    SimpleEventBus.BUS().subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandler(MonoRepoEventStore.STORE()));
    SimpleEventBus.BUS().subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandlerForQuery(bank));
  }
}
