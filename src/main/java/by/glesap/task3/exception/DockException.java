package by.glesap.task3.exception;

public class DockException extends Exception {
  public DockException(String message) {
    super(message);
  }

  public DockException(String message, Throwable cause) {
    super(message, cause);
  }

  public DockException(Throwable cause) {
    super(cause);
  }
}
