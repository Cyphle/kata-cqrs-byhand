package fr.cqrsbyhand.acceptance;

import fr.cqrsbyhand.command.bus.CommandBus;
import fr.cqrsbyhand.command.bus.SimpleCommandBus;
import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.services.AccountQuery;
import fr.cqrsbyhand.query.services.AccountReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreationAcceptanceTest {
  private AccountQuery accountReader;
  private CommandBus commandBus;

  @Before
  public void setUp() throws Exception {
    commandBus = SimpleCommandBus.BUS;
    ApplicationConfig.CONFIG.initialize();

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
  }
}
