package fr.cqrsbyhand.command;

public class CommandResult<T> {
  private T payload;

  public T getPayload() {
    return payload;
  }
}
