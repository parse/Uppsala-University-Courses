package symbolic;

public abstract class Unary extends Sexpr {
	protected Sexpr arg;
	private static String unaries[] = {"-", "&", "\"", "sin", "cos", "exp", "log"};
	
	public Unary (Sexpr arg) {
		this.arg = arg; 
	}
	
	public String toString () {		
		return this.getName() + "(" + arg.toString() + ")";
	}
	
	public static boolean isUnaryIdent(String s, int c) {
		
		if (s == null)
			s = String.valueOf( (char) c );
			
		for (String unary : unaries) {
			if (unary.equals(s))
				return true;
		}
		
		return false;
	}
}
