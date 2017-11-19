package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;

public class AccountCreditCommandHandler extends CommandHandler {
  public AccountCreditCommandHandler() {
    super(SimpleEventBus.BUS, MonoRepoEventStore.STORE);
  }

  @Override
  public void handle(Command command) {

  }
}
