package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreditCommand;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountCreditError;
import fr.cqrsbyhand.exceptions.CommandException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreditCommandHandlerTest {
  @Mock
  private EventStore eventStore;
  @Mock
  private EventBus eventBus;
  @Mock
  private Denormalizer<Account> denormalizer;
  private AccountCreditCommandHandler accountCreditHandler;

  @Before
  public void setUp() throws Exception {
    accountCreditHandler = new AccountCreditCommandHandler(eventBus, eventStore, denormalizer);
  }

  @Test
  public void should_throw_error_if_account_does_not_exists() throws Exception {
    AccountCreditCommand accountCreditCommand = new AccountCreditCommand("abcuid", 200);

    given(eventStore.getEventsOf(accountCreditCommand.getAccountId())).willReturn(Collections.emptyList());

    try {
      // When
      accountCreditHandler.handle(accountCreditCommand);
      fail("It should throw an exception if account does not exist");
    } catch (CommandException ex) {
      // Then
      assertThat((AccountCreditError) ex.getError()).isEqualTo(new AccountCreditError("Account already does not exist", "abcuid"));
    }
  }

  @Test
  public void should_credit_account() throws Exception {
    // Given
    AccountCreditCommand accountCreditCommand = new AccountCreditCommand("abcuid", 200);
    given(eventStore.getEventsOf(accountCreditCommand.getAccountId())).willReturn(Collections.singletonList(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abcuid", "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0))));
    // When
    accountCreditHandler.handle(accountCreditCommand);
    // Then
    verify(eventBus).apply(new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abcuid", 200));
  }
}
