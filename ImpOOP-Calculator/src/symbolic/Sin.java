package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Sin extends Unary {
	public Sin(Sexpr arg) {
		super(arg);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return Symbolic.sin(arg.eval(t));
	}
	
	protected String getName() {
		return "sin";
	}
	
	protected Sexpr diff(Sexpr var) {
		return Symbolic.multiply(arg.diff(var), Symbolic.cos(arg));
	}

}
