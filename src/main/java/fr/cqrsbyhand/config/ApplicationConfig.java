package fr.cqrsbyhand.config;

import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.utils.AccountIdGenerator;

public class ApplicationConfig {
  public void initialize() {
    registerCommandHandlers();
  }

  private void registerCommandHandlers() {
    SimpleCommandBus.BUS.subscribe(
            AccountCreationCommand.class,
            new AccountCreationCommandHandler(
                    SimpleEventBus.BUS,
                    MonoRepoEventStore.STORE,
                    new AccountDenormalizer(),
                    new AccountIdGenerator()
            ));
  }
  /*
  HERE WILL HAVE ALL REGISTER:
  - to command bus
  - to event bus
   */
}
