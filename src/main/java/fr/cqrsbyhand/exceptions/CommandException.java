package fr.cqrsbyhand.exceptions;

public class CommandException extends Exception {
  private final CommandError error;

  public CommandException(CommandError error) {
    super("Command exception");
    this.error = error;
  }

  public CommandError getError() {
    return error;
  }
}
