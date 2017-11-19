package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.exceptions.AccountCreationError;
import fr.cqrsbyhand.exceptions.CommandException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class AccountCreationCommandHandlerTest {
  @Test
  public void should_throw_command_exception_if_account_name_already_exists() throws Exception {
    // Given
    Command accountCreationCommand = new AccountCreationCommand("My super account");
    CommandHandler accountCreationHandler = new AccountCreationCommandHandler();
    try {
      // When
      accountCreationHandler.handle(accountCreationCommand);
      fail("It should throw an exception if account already exists");
    } catch (CommandException ex) {
      // Then
      assertThat((AccountCreationError) ex.getError()).isEqualTo(new AccountCreationError("Account already exists", "My super account"));
    }
  }
}
