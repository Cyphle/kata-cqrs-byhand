package fr.cqrsbyhand.acceptance;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.command.commands.AccountDebitCommand;
import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.AccountDebitedEvent;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.services.AccountQuery;
import fr.cqrsbyhand.query.services.AccountReader;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDebitAcceptanceTest {
  private AccountQuery accountReader;
  private CommandBus commandBus;

  @Before
  public void setUp() throws Exception {
    ApplicationConfig.CONFIG.initialize();
    commandBus = ApplicationConfig.getCommandBus();
    ApplicationConfig.getBank().clear();
    ApplicationConfig.getEventStore().clearEvents();

    accountReader = new AccountReader(ApplicationConfig.getBank());

    AccountCreationCommand command = new AccountCreationCommand("My super account");
    commandBus.send(command);
    AccountCreditCommand creditCommand = new AccountCreditCommand("abcuid", 200);
    commandBus.send(creditCommand);
  }

  @Test
  public void should_credit_an_account() throws Exception {
    // Given
    AccountDebitCommand command = new AccountDebitCommand("abcuid", 100);
    // When
    commandBus.send(command);
    AccountView account = accountReader.getAccountByName("My super account");
    // Then
    AccountView expectedAccount = new AccountView();
    expectedAccount.setId("abcuid");
    expectedAccount.setName("My super account");
    expectedAccount.setBalance(100.0);
    assertThat(account).isEqualTo(expectedAccount);
    assertThat(ApplicationConfig.getEventStore().getAllEvents()).containsExactly(
            new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abcuid", "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0)),
            new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abcuid", 200, LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0)),
            new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "abcuid", 100, LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0))
    );
  }
}
