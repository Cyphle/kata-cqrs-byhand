package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountDebitCommand;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.AccountDenormalizer;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountDebitError;
import fr.cqrsbyhand.exceptions.CommandException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountDebitCommandHandlerTest {
  @Mock
  private EventStore eventStore;
  @Mock
  private EventBus eventBus;
  private Denormalizer<Account> denormalizer;
  private AccountDebitCommandHandler accountDebitHandler;

  @Before
  public void setUp() throws Exception {
    denormalizer = new AccountDenormalizer();
    accountDebitHandler = new AccountDebitCommandHandler(eventBus, eventStore, denormalizer);
  }

  @Test
  public void should_throw_error_if_account_does_not_exists() throws Exception {
    AccountDebitCommand accountDebitCommand = new AccountDebitCommand("abcuid", 100);

    given(eventStore.getEventsOf(accountDebitCommand.getAccountId())).willReturn(Collections.emptyList());

    try {
      // When
      accountDebitHandler.handle(accountDebitCommand);
      fail("It should throw an exception if account does not exist");
    } catch (CommandException ex) {
      // Then
      assertThat((AccountDebitError) ex.getError()).isEqualTo(new AccountDebitError("Account already does not exist", "abcuid"));
    }
  }

  @Test
  public void should_throw_error_if_debit_amount_is_too_high_for_balance() throws Exception {
    AccountDebitCommand accountDebitCommand = new AccountDebitCommand("abcuid", 100);

    given(eventStore.getEventsOf(accountDebitCommand.getAccountId())).willReturn(Collections.singletonList(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abcuid", "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0))));

    try {
      // When
      accountDebitHandler.handle(accountDebitCommand);
      fail("It should throw an exception if account does not exist");
    } catch (CommandException ex) {
      // Then
      assertThat((AccountDebitError) ex.getError()).isEqualTo(new AccountDebitError("You cannot have negative balance", "My super account"));
    }
  }
}
