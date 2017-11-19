package fr.cqrsbyhand.event.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class AccountCreditedEvent implements Event {
  private String accountId;
  private int amount;
  private LocalDateTime eventDate;

  public AccountCreditedEvent(String accountId, int amount, LocalDateTime eventDate) {
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
}
