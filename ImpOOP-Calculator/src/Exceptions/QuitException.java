package Exceptions;

public class QuitException extends RuntimeException {
	public QuitException() {
		super();
	}
	
	public QuitException(String message) {
		super(message);
	}
}
