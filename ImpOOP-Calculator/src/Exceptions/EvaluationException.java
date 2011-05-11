package Exceptions;

public class EvaluationException extends RuntimeException {
	public EvaluationException() {
		super();
	}
	
	public EvaluationException(String message) {
		super(message);
	}
}
