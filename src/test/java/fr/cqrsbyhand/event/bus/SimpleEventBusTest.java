package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.events.EventType;
import fr.cqrsbyhand.event.store.MonoRepoEventStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEventBusTest {
  private EventBus eventBus;

  @Before
  public void setUp() throws Exception {
    ApplicationConfig.CONFIG.initializeEventBus();
    eventBus = SimpleEventBus.BUS();
    MonoRepoEventStore.STORE.clearEvents();
  }

  @Test
  public void should_store_event_in_event_store() throws Exception {
    eventBus.apply(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account"));

    assertThat(MonoRepoEventStore.STORE.getAllEvents()).containsExactly(new AccountCreatedEvent(EventType.ACCOUNT_CREATION, "abc", "My account", LocalDateTime.of(2017, Month.NOVEMBER, 19, 17, 0)));
  }
}
