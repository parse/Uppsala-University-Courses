package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Cos extends Unary {
	
	public Cos(Sexpr arg) {
		super(arg);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return Symbolic.cos(arg.eval(t));
	}
	
	protected String getName() {
		return "cos";
	}
	
	protected Sexpr diff(Sexpr var) {
		return Symbolic.negate(Symbolic.sin(arg.diff(var)));
	}

}
