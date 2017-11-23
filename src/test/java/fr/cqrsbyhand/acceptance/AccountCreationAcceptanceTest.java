package fr.cqrsbyhand.acceptance;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
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

@RunWith(MockitoJUnitRunner.class)
public class AccountCreationAcceptanceTest {
  private AccountQuery accountReader;
  private CommandBus commandBus;

  @Before
  public void setUp() throws Exception {
    ApplicationConfig.CONFIG.initialize();
    commandBus = ApplicationConfig.getCommandBus();

    accountReader = new AccountReader(ApplicationConfig.getBank());
  }

  @Test
  public void should_create_a_new_account() throws Exception {
    // Given
    AccountCreationCommand command = new AccountCreationCommand("My super account");
    // When
    commandBus.send(command);
    AccountView account = accountReader.getAccountByName("My super account");
    // Then
    AccountView expectedAccount = new AccountView();
    expectedAccount.setId("abcuid");
    expectedAccount.setName("My super account");
    expectedAccount.setBalance(0.0);
    assertThat(account).isEqualTo(expectedAccount);
    assertThat(ApplicationConfig.getEventStore().getAllEvents()).containsExactly(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abcuid", "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0)));
  }
}
