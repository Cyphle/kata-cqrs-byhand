package fr.cqrsbyhand.event.bus;

import fr.cqrsbyhand.config.ApplicationConfig;
import fr.cqrsbyhand.event.events.AccountCreatedEvent;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandler;
import fr.cqrsbyhand.event.handlers.AccountCreatedEventHandlerForQuery;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleEventBusSubscribeTest {
  @Before
  public void setUp() throws Exception {
    ApplicationConfig.CONFIG.initializeEventBus();
    SimpleEventBus.BUS().emptySubscribers();
  }

  @Test
  public void should_allow_subscription_of_a_handler_to_an_event() throws Exception {
    // Given
    EventBus eventBus = SimpleEventBus.BUS();
    // When
    eventBus.subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandler());
    // Then
    assertThat(eventBus.getSubscribers()).containsExactly(new EventBusSubscriber(AccountCreatedEvent.class, new AccountCreatedEventHandler()));
  }

  @Test
  public void should_allow_subscription_of_multiple_handlers_to_same_event() throws Exception {
    // Given
    EventBus eventBus = SimpleEventBus.BUS();
    // When
    eventBus.subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandler());
    eventBus.subscribe(AccountCreatedEvent.class, new AccountCreatedEventHandlerForQuery());
    // Then
    assertThat(eventBus.getSubscribersOf(AccountCreatedEvent.class)).containsExactlyInAnyOrder(
            new EventBusSubscriber(AccountCreatedEvent.class, new AccountCreatedEventHandler()),
            new EventBusSubscriber(AccountCreatedEvent.class, new AccountCreatedEventHandlerForQuery())
    );
  }
}
