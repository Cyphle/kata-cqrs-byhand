package fr.cqrsbyhand.event.events;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class AccountCreatedEvent implements Event {
  private String accountId;
  private String accountName;
  private LocalDateTime eventDate;

  public AccountCreatedEvent(String accountId, String accountName, LocalDateTime eventDate) {
    this.accountId = accountId;
    this.accountName = accountName;
    this.eventDate = eventDate;
  }

  @Override
  public String getAccountId() {
    return accountId;
  }

  @Override
  public String getAccountName() {
    return accountName;
  }

  @Override
  public int getAmount() {
    return 0;
  }

  @Override
  public LocalDateTime getEventDate() {
    return eventDate;
  }
}
