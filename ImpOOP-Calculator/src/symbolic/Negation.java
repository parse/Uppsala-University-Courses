package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Negation extends Unary {
	public Negation(Sexpr arg) {
		super(arg);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return Symbolic.negate(arg.eval(t));
	}
	
	protected String getName() {
		return "-";
	}

	protected Sexpr diff(Sexpr var) {
		return Symbolic.multiply(new Constant(-1), arg.diff(var));
	}
}
