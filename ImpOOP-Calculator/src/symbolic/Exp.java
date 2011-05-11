package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Exp extends Unary {
	public Exp(Sexpr arg) {
		super(arg);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return Symbolic.exp(arg.eval(t));
	}

	protected Sexpr diff(Sexpr var) {
		return Symbolic.multiply(arg.diff(var), new Exp(arg));
	}
	
	protected String getName() {
		return "exp";
	}
}
