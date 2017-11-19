package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.exceptions.CommandException;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class AccountCreationCommandHandler extends CommandHandler {
  public AccountCreationCommandHandler() {
    super(SimpleEventBus.BUS, MonoRepoEventStore.STORE);
  }

  @Override
  public void handle(Command command) throws CommandException {
    List<Event> events = this.eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION);
//    denormalizer.project(events);
    /*
    - Get all account aggregate (from event store)
    - Check name is not used (send result to denormalizer)
    - Send event account created to eventBus
     */
  }
}
