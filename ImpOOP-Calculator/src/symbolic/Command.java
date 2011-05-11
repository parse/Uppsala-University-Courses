package symbolic;

import Exceptions.SyntaxException;

public abstract class Command extends Sexpr {
	public Command () {

	}
	
	public static boolean isCommand (String s) { 
		return s.equals("quit") || s.equals("vars");
	}
	
	public String toString() {
		return this.getName();
	}
	
	protected Sexpr diff(Sexpr var) {
		throw new SyntaxException("Derivering kan ej utföras på kommandon");
	}
}
