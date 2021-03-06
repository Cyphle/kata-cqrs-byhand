package fr.cqrsbyhand.command.handlers;

import fr.cqrsbyhand.command.commands.AccountCreationCommand;
import fr.cqrsbyhand.command.commands.Command;
import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.domain.denormalizers.Denormalizer;
import fr.cqrsbyhand.event.bus.EventBus;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.exceptions.AccountCreationError;
import fr.cqrsbyhand.exceptions.CommandException;
import fr.cqrsbyhand.utils.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class AccountCreationCommandHandlerTest {
  @Mock
  private EventBus eventBus;
  @Mock
  private EventStore eventStore;
  @Mock
  private Denormalizer denormalizer;
  @Mock
  private IdGenerator idGenerator;
  private AccountCreationCommandHandler accountCreationHandler;

  @Before
  public void setUp() throws Exception {
    accountCreationHandler = new AccountCreationCommandHandler(eventBus, eventStore, denormalizer, idGenerator);
  }

  @Test
  public void should_throw_command_exception_if_account_name_already_exists() throws Exception {
    // Given
    Command accountCreationCommand = new AccountCreationCommand("My super account");

    given(eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION)).willReturn(Collections.singletonList(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "a", "My super account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0))));
    Map<String, Account> accounts = new HashMap<>();
    accounts.put("a", new Account("a", LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0), LocalDateTime.of(2017, Month.NOVEMBER, 19, 15, 0), "My super account", 0));
    given(denormalizer.project(any(List.class))).willReturn(accounts);

    try {
      // When
      accountCreationHandler.handle(accountCreationCommand);
      fail("It should throw an exception if account already exists");
    } catch (CommandException ex) {
      // Then
      assertThat((AccountCreationError) ex.getError()).isEqualTo(new AccountCreationError("Account already exists", "My super account"));
    }
  }

  @Test
  public void should_create_an_account() throws Exception {
    // Given
    Command accountCreationCommand = new AccountCreationCommand("My super account");
    given(idGenerator.generateUuid()).willReturn("abc");
    // When
    accountCreationHandler.handle(accountCreationCommand);
    // Then
    verify(eventBus).apply(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My super account"));
  }
}
