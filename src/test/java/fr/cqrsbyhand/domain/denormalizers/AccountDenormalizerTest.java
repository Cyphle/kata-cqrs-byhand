package fr.cqrsbyhand.domain.denormalizers;

import fr.cqrsbyhand.domain.aggregates.Account;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.AccountCreditedEvent;
import fr.cqrsbyhand.event.events.AccountDebitedEvent;
import fr.cqrsbyhand.event.events.Event;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDenormalizerTest {
  private Denormalizer<Account> denormalizer;

  @Before
  public void setUp() throws Exception {
    denormalizer = new AccountDenormalizer();
  }

  @Test
  public void should_sort_events_by_oldest_date_first() throws Exception {
    List<Event> events = Arrays.asList(
            new AccountCreatedEvent("a", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 15, 10, 12)),
            new AccountCreditedEvent("a", 100, LocalDateTime.of(2017, Month.NOVEMBER, 15, 11, 20)),
            new AccountCreditedEvent("a", 300, LocalDateTime.of(2017, Month.NOVEMBER, 16, 12, 20)),
            new AccountDebitedEvent("a", 50, LocalDateTime.of(2017, Month.NOVEMBER, 17, 15, 18)),
            new AccountDebitedEvent("a", 50, LocalDateTime.of(2017, Month.NOVEMBER, 16, 8, 18)),
            new AccountCreditedEvent("a", 300, LocalDateTime.of(2017, Month.NOVEMBER, 16, 18, 20))
    );

    List<Event> sortedEvents = denormalizer.sortEvents(events);

    assertThat(sortedEvents).containsExactly(
            new AccountCreatedEvent("a", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 15, 10, 12)),
            new AccountCreditedEvent("a", 100, LocalDateTime.of(2017, Month.NOVEMBER, 15, 11, 20)),
            new AccountDebitedEvent("a", 50, LocalDateTime.of(2017, Month.NOVEMBER, 16, 8, 18)),
            new AccountCreditedEvent("a", 300, LocalDateTime.of(2017, Month.NOVEMBER, 16, 12, 20)),
            new AccountCreditedEvent("a", 300, LocalDateTime.of(2017, Month.NOVEMBER, 16, 18, 20)),
            new AccountDebitedEvent("a", 50, LocalDateTime.of(2017, Month.NOVEMBER, 17, 15, 18))
    );
  }
}
