package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreatedEventHandlerForQueryTest {
  @Mock
  private Bank bank;

  private AccountCreatedEventHandlerForQuery accountCreatedEventHandler;

  @Before
  public void setUp() throws Exception {
    accountCreatedEventHandler = new AccountCreatedEventHandlerForQuery(bank);
  }

  @Test
  public void should_create_a_new_account_in_the_bank() throws Exception {
    Event event = new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 23, 20, 0));
    doNothing().when(bank).createAccount(any(AccountView.class));

    accountCreatedEventHandler.handle(event);

    AccountView account = new AccountView();
    account.setId("abc");
    account.setName("My account");
    account.setBalance(0);
    verify(bank).createAccount(account);
  }
}
