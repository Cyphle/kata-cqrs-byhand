package fr.cqrsbyhand.command.bus;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.commandhandlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.domain.commandhandlers.AccountCreditCommandHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCommandBusTest {
  @Mock
  private
  AccountCreationCommandHandler accountCreationCommandHandler;
  @Mock
  private
  AccountCreditCommandHandler accountCreditCommandHandler;

  private CommandBus commandBus;

  @Before
  public void setUp() throws Exception {
    commandBus = SimpleCommandBus.BUS;
    commandBus.subscribe(AccountCreationCommand.class, accountCreationCommandHandler);
    commandBus.subscribe(AccountCreditCommand.class, accountCreditCommandHandler);
  }

  @Test
  public void should_send_command_to_the_right_handler() throws Exception {
    // Given
    Command accountCreationCommand = new AccountCreationCommand("My account");
    // When
    commandBus.send(accountCreationCommand);
    // Then
    verify(accountCreationCommandHandler).handle(accountCreationCommand);
    verify(accountCreditCommandHandler, never()).handle(accountCreationCommand);
  }
}
