package fr.cqrsbyhand.query.models;

import fr.cqrsbyhand.domain.aggregates.Account;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AccountView {
  private String id;
  private String name;
  private double balance;

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public String getName() {
    return name;
  }

  public static AccountView fromAccountAggregate(Account account) {
    AccountView accountView = new AccountView();
    accountView.setId(account.getAccountId());
    accountView.setName(account.getAccountName());
    accountView.setBalance(account.getBalance());
    return accountView;
  }
}
