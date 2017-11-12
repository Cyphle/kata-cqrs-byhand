package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleCommandBusSubscribeTest {
  @Test
  public void should_subscribe_command_handler_to_bus() throws Exception {
    // Given
    CommandBus commandBus = SimpleCommandBus.BUS;
    // When
    commandBus.subscribe(AccountCreationCommand.class, new AccountCreationCommandHandler());
    // Then
    Map<Class<AccountCreationCommand>, AccountCreationCommandHandler> subscribers = new HashMap<>();
    subscribers.put(AccountCreationCommand.class, new AccountCreationCommandHandler());
    assertThat(commandBus.getSubscribers()).isEqualTo(subscribers);
  }
}
