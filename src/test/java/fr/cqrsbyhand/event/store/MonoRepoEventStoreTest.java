package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.*;
import fr.cqrsbyhand.mocks.MockEventRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MonoRepoEventStoreTest {
  @Before
  public void setUp() throws Exception {
    MonoRepoEventStore.initialize(new MockEventRepository());
    ((MonoRepoEventStore) MonoRepoEventStore.STORE()).clearEvents();
  }

  @Test
  public void should_return_events_of_a_given_type() throws Exception {
    EventStore eventStore = MonoRepoEventStore.STORE();
    eventStore.save(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0)));
    eventStore.save(new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abc", 300, LocalDateTime.of(2017, Month.NOVEMBER, 20, 18, 0)));
    eventStore.save(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "abc", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0)));

    List<Event> events = eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION);
    assertThat(events).containsExactly(
            new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0))
    );
  }

  @Test
  public void should_return_all_events_of_account() throws Exception {
    EventStore eventStore = MonoRepoEventStore.STORE();
    eventStore.save(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0)));
    eventStore.save(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "def", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0)));
    eventStore.save(new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abc", 300, LocalDateTime.of(2017, Month.NOVEMBER, 20, 18, 0)));
    eventStore.save(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "abc", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0)));
    eventStore.save(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "zzz", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0)));
    eventStore.save(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "yyy", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0)));

    List<Event> events = eventStore.getEventsOf("abc");

    assertThat(events).containsExactly(
            new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0)),
            new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abc", 300, LocalDateTime.of(2017, Month.NOVEMBER, 20, 18, 0)),
            new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "abc", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0))
    );
  }
}
