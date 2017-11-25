package fr.cqrsbyhand.event.handlers;

import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.Event;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.EventStore;
import fr.cqrsbyhand.query.models.AccountView;
import fr.cqrsbyhand.query.repositories.Bank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountCreditedEventHandlerForQueryTest {
  @Mock
  private Bank bank;
  @Mock
  private EventStore eventStore;

  private AccountCreditedEventHandlerForQuery accountCreditedEventHandler;

  @Before
  public void setUp() throws Exception {
    accountCreditedEventHandler = new AccountCreditedEventHandlerForQuery(bank, eventStore);
  }

  @Test
  public void should_update_account_with_new_balance() throws Exception {
    Event event = new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abc", 200, LocalDateTime.of(2017, Month.NOVEMBER, 23, 20, 0));
    doNothing().when(bank).updateAccount(any(AccountView.class));
    given(eventStore.getEventsOf("abc")).willReturn(
            Collections.singletonList(new AccountCreatedEvent(
                    EventType.ACCOUNT_CREATION,
                    "abc",
                    "My account",
                    LocalDateTime.of(2017, Month.NOVEMBER, 23, 20, 0)
            ))
    );

    accountCreditedEventHandler.handle(event);

    AccountView account = new AccountView();
    account.setId("abc");
    account.setName("My account");
    account.setBalance(200);
    verify(bank).updateAccount(account);
  }
}
