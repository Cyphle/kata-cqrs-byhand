package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.bus.SimpleEventBus;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import fr.cqrsbyhand.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCommandBusSubscribeTest {
  @Mock
  private Denormalizer denormalizer;
  @Mock
  private IdGenerator idGenerator;

  @Test
  public void should_subscribe_command_handler_to_bus() throws Exception {
    // Given
    CommandBus commandBus = SimpleCommandBus.BUS;
    // When
    commandBus.subscribe(AccountCreationCommand.class, new AccountCreationCommandHandler(SimpleEventBus.BUS, MonoRepoEventStore.STORE, denormalizer, idGenerator));
    // Then
    Map<Class<AccountCreationCommand>, AccountCreationCommandHandler> subscribers = new HashMap<>();
    subscribers.put(AccountCreationCommand.class, new AccountCreationCommandHandler(SimpleEventBus.BUS, MonoRepoEventStore.STORE, denormalizer, idGenerator));
    assertThat(commandBus.getSubscribers()).isEqualTo(subscribers);
  }
}
