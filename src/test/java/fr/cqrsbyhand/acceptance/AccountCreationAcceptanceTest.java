package fr.cqrsbyhand.acceptance;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.command.handlers.AccountCreationCommandHandler;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.services.AccountQuery;
import fr.cqrsbyhand.query.services.AccountReader;
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
  private AccountQuery accountReader;
  private CommandBus commandBus;
  private AccountCreationCommandHandler accountCreationCommandHandler;
  private EventBus eventBus;
  private ApplicationConfig applicationConfig;

  @Before
  public void setUp() throws Exception {
    commandBus = SimpleCommandBus.BUS;
    ApplicationConfig.CONFIG.initialize();

    accountReader = new AccountReader();
  }

  @Test
  public void should_create_a_new_account() throws Exception {
    // Given
    AccountCreationCommand command = new AccountCreationCommand("My super account");
    String accountId = "abcuid";
    AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(EventType.ACCOUNT_CREATION, accountId, "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 12, 10, 1, 10));
    // When
    commandBus.send(command);
    AccountView account = accountReader.getAccountByName("My super account");
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
