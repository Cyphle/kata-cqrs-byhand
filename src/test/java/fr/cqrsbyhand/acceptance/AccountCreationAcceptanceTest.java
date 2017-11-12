package fr.cqrsbyhand.acceptance;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.CommandResult;
import fr.cqrsbyhand.command.bus.EventBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.events.AccountCreatedEvent;
import fr.cqrsbyhand.domain.commandhandlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.services.AccountQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreationAcceptanceTest {
  private AccountQueryService accountService;
  private CommandBus commandBus;
  private AccountCreationCommandHandler accountCreationCommandHandler;
  private EventBus eventBus;

  @Before
  public void setUp() throws Exception {
//    cqrsbyhandApplicationConfig.initialize();
//
//    commandBus = SimpleCommandBus.getInstance();
  }

  @Test
  public void should_create_a_new_account() throws Exception {
    // Given
    AccountCreationCommand command = new AccountCreationCommand("My super account");
    String accountId = "abcuid";
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(accountId, "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 12, 10, 1, 10));
    // When
    CommandResult result = commandBus.send(command);
    AccountView account = accountService.getAccount((String) result.getPayload());
    // Then
    AccountView expectedAccount = new AccountView();
    expectedAccount.setId("abcuid");
    expectedAccount.setName("My super account");
    expectedAccount.setBalance(0.0);

    verify(accountCreationCommandHandler).handle(command);
    verify(eventBus).apply(accountCreatedEvent);
    assertThat(account).isEqualTo(expectedAccount);
  }
  /*
  1st version without command bus nor event bus
  - Send command new account
  - Service get command and instantiate needed object
  - Call object (== domain)
  - Event is emitted
   */

  /*
  Acceptance is:
  - Send account creation request
  - Get account list
  - Has account with 0 as initial balance
   */

  /*
  Need config class that is initialized at startup (creation of singleton, registering)
   */
}
