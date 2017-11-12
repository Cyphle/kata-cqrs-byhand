package fr.cqrsbyhand.query.models;

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
}
