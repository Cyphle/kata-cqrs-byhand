package fr.cqrsbyhand.event.store;

import fr.cqrsbyhand.event.events.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MonoRepoEventStoreTest {
  @Test
  public void should_return_events_of_a_given_type() throws Exception {
    EventStore eventStore = MonoRepoEventStore.STORE;
    eventStore.save(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0)));
    eventStore.save(new AccountCreditedEvent(EventType.ACCOUNT_CREDIT, "abc", 300, LocalDateTime.of(2017, Month.NOVEMBER, 20, 18, 0)));
    eventStore.save(new AccountDebitedEvent(EventType.ACCOUNT_DEBIT, "abc", 50, LocalDateTime.of(2017, Month.NOVEMBER, 21, 18, 0)));

    List<Event> events = eventStore.getAllEventsByType(EventType.ACCOUNT_CREATION);
    assertThat(events).containsExactly(
      new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 18, 0))
    );
  }
}
