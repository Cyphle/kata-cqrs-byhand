package fr.cqrsbyhand.config;

import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandler;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.utils.AccountIdGenerator;
import fr.cqrsbyhand.utils.Clock;

public enum ApplicationConfig {
  CONFIG;

  private EventBus eventBus;

  public void initialize() {
    initializeEventBus();
    registerCommandHandlers();
    registerEventHandlers();
  }

  public void initializeEventBus() {
    SimpleEventBus.initializeBus(MonoRepoEventStore.STORE, new Clock());
    eventBus = SimpleEventBus.BUS();
  }

  private void registerCommandHandlers() {
    SimpleCommandBus.BUS.subscribe(
            AccountCreationCommand.class,
            new AccountCreationCommandHandler(
                    eventBus,
                    MonoRepoEventStore.STORE,
                    new AccountDenormalizer(),
                    new AccountIdGenerator()
            ));
  }

  private void registerEventHandlers() {
    eventBus.subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandler());
  }
}
