package fr.cqrsbyhand.event.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class AccountDebitedEvent implements Event {
  private final EventType type;
  private final String accountId;
  private final int amount;
  private LocalDateTime eventDate;

  public AccountDebitedEvent(EventType type, String accountId, int amount) {
    this.type = type;
    this.accountId = accountId;
    this.amount = amount;
  }

  public AccountDebitedEvent(EventType type, String accountId, int amount, LocalDateTime eventDate) {
    this.type = type;
    this.accountId = accountId;
    this.amount = amount;
    this.eventDate = eventDate;
  }

  @Override
  public String getAccountId() {
    return accountId;
  }

  @Override
  public String getAccountName() {
    return "";
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public LocalDateTime getEventDate() {
    return eventDate;
  }

  @Override
  public EventType getEventType() {
    return type;
  }

  @Override
  public void setEventDate(LocalDateTime newEventDate) {
    eventDate = newEventDate;
  }
}
