package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Evaluation extends Unary {
	public Evaluation(Sexpr arg) {
		super(arg);
		this.priority = 1;
	}

	protected Sexpr diff(Sexpr var) {
		return arg.diff(var);
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return arg.eval(t).eval(t);
	}
	
	protected String getName() {
		return "&";
	}
}
