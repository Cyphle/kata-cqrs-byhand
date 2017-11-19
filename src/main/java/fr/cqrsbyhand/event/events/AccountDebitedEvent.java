package fr.cqrsbyhand.event.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class AccountDebitedEvent implements Event {
  private String accountId;
  private int amount;
  private LocalDateTime eventDate;

  public AccountDebitedEvent(String accountId, int amount, LocalDateTime eventDate) {
    this.accountId = accountId;
    this.amount = amount;
    this.eventDate = eventDate;
  }

  @Override
  public LocalDateTime getEventDate() {
    return eventDate;
  }
}
